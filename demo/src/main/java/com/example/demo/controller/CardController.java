package com.example.demo.controller;

import com.example.demo.config.constant.TransactionType;
import com.example.demo.dto.card.CardReqDto;
import com.example.demo.dto.message.BasicRequestDto;
import com.example.demo.dto.message.BasicResponse;
import com.example.demo.dto.topup.TopupResDto;
import com.example.demo.service.CardService;
import com.example.demo.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("card")
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class CardController {
    private TransactionService transactionService;
    private CardService cardService;

    @PostMapping("buyCard")
    public BasicResponse buyCard(@RequestBody CardReqDto request) {
        return transactionService
                .manageTransaction(request, TransactionType.BUY_CARD)
                .apply(
                        (partner, publicKey, privateKey, transaction) -> cardService
                                .getCardDetailsFromRequest(request)
                                .flatMap(cardDetails -> cardService.getEncryptedCardList(
                                                        request, partner,
                                                        cardDetails, transaction, publicKey
                                                )
                                                .map(cardsInfo -> cardService
                                                        .toResponse(cardsInfo, partner,
                                                                cardDetails, transaction)
                                                )
                                )
                );
    }

    @PostMapping("directTopup")
    public BasicResponse directTopup(@RequestBody TopupResDto request) {
        return transactionService
                .manageTransaction(request, TransactionType.TOP_UP)
                .apply((partner, publicKey, privateKey, transaction) -> null);
    }

    @PostMapping("checkTransaction")
    public BasicResponse checkTransaction(@RequestBody BasicRequestDto request) {
        return transactionService
                .manageTransaction(request, TransactionType.CHECK_TRANSACTION)
                .apply((partner, publicKey, privateKey, transaction) -> null);
    }

    @PostMapping("retrieveCardInfo")
    public BasicResponse retrieveCardInfo(BasicRequestDto request) {
        //TODO: write logic
        return null;
    }

    @PostMapping("checkProductInfo")
    //Check amount of product that available
    public BasicResponse checkProductInfo(BasicRequestDto request) {
        //TODO: write logic
        return null;
    }
}
