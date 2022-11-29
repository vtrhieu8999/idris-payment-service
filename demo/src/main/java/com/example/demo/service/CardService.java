package com.example.demo.service;

import com.example.demo.config.ErrorCode;
import com.example.demo.dto.card.BuyCardReq;
import com.example.demo.dto.card.CardInfo;
import com.example.demo.dto.card.CardResDto;
import com.example.demo.exception.*;
import com.example.demo.model.Partner;
import com.example.demo.model.Transaction;
import com.example.demo.model.product.Card;
import com.example.demo.model.product.CardDetails;
import com.example.demo.repo.CardConfigRepo;
import com.example.demo.repo.CardRepo;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class CardService {
    private final CardRepo cardRepo;
    private final CardConfigRepo cardConfigRepo;
    private final EncryptService encryptService;

    private boolean isEnoughMoney(Partner partner, CardDetails cardDetails, int amount) {
        return partner.getBalance() >= cardDetails.getValue() * (1 - cardDetails.getDiscountPercent() / 100) * amount;
    }

    private boolean isEnoughCard(CardDetails cardDetails, int amount) {
        return cardRepo.countByCardConfigIdAndTransIdIsNull(cardDetails.getId()) >= amount;
    }

    private Option<ApplicationException> isPurchasable(int quantity, Partner partner, CardDetails cardDetails) {
        return !isEnoughMoney(partner, cardDetails, quantity) ?
                Option.of(new NotEnoughMoneyException("Not enough money to buy")) :
                !isEnoughCard(cardDetails, quantity) ?
                        Option.of(new NotEnoughCardException("Not enough card to buy")) :
                        Option.none();
    }

    private ProductNotExistException toProductException(String message) {
        return new ProductNotExistException(message);
    }

    private <T> Try<T> optionToTry(Option<T> product, String message) {
        return product.fold(
                () -> Try.failure(toProductException(message)),
                Try::success
        );
    }

    private Try<Tuple2<Integer, String>> formatProductCode(String productCode) {
        try {
            final String[] part = productCode.split("(?<=\\D)(?=\\d)");
            return Try.success(Tuple.of(Integer.parseInt(part[1]), part[0]));
        } catch (Exception e) {
            return Try.failure(toProductException("ProductCode's format is wrong"));
        }
    }

    private Try<CardDetails> transformProductCode(String productCode) {
        return formatProductCode(productCode)
                .flatMap(tuple -> optionToTry(
                        cardConfigRepo.findCardConfigByPriceTagAndTelcoCode(tuple._1, tuple._2),
                        "Cannot find product"
                ));
    }

    public Try<CardDetails> getCardDetailsFromRequest(BuyCardReq request) {
        return optionToTry(request.optionProductCode(), "ProductCode is null")
                .flatMap(this::transformProductCode);
    }

    private List<Card> getListOfAvailableCard(int quantity, CardDetails cardDetails) {
        return cardRepo.getAvailableCardList(cardDetails.getId(), quantity);
    }

    private Try<List<CardInfo>> encryptPinCodeByBatch(PublicKey publicKey, List<Card> cardList, String transactionId) {
        return Try.traverse(cardList,
                        card -> encryptService.encryptMessage(
                                        publicKey,
                                        card.getPinCode()
                                )
                                .map(card::setPinCode)
                                .map(t -> t.setIsAvailable(false)
                                        .setTransId(transactionId))
                )
                .map(Seq::toJavaList)
                .mapTry(cardRepo::saveAll)
                .map(List::stream)
                .map(str -> str.map(CardInfo::new).collect(Collectors.toList()));
    }


    public Try<List<CardInfo>> getEncryptedCardList(BuyCardReq cardReq,
                                                    Partner partner,
                                                    CardDetails cardDetails,
                                                    Transaction transaction,
                                                    PublicKey publicKey) {
        return cardReq
                .optionQuantity()
                .fold(
                        () -> Try.failure(new ValidateException("quantityIsNull")),
                        quantity -> isPurchasable(quantity, partner, cardDetails).fold(
                                () -> encryptPinCodeByBatch(publicKey,
                                        getListOfAvailableCard(quantity, cardDetails),
                                        transaction.getTransId()),
                                Try::failure
                        )
                );
    }

    public CardResDto toResponse(List<CardInfo> cardsInfo,
                                 Partner partner,
                                 CardDetails cardDetails,
                                 Transaction transaction) {
        CardResDto result = new CardResDto();
        result.setCardList(cardsInfo);
        result.setResCode(ErrorCode.SUCCESS);
        result.setPartnerCode(partner.getPartnerCode());
        result.setPartnerTransId(transaction.getTransId());
        result.setTotalValue(cardDetails.getValue() * result.getCardList().size());
        result.setDiscountValue(result.getTotalValue() * cardDetails.getDiscountPercent().longValue() / 100);
        result.setDebitValue(result.getTotalValue() - result.getDiscountValue());
        return result;
    }

}
