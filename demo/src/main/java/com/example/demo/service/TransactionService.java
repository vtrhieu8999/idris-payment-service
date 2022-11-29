package com.example.demo.service;

import com.example.demo.config.constant.TransactionStatus;
import com.example.demo.config.constant.TransactionType;
import com.example.demo.dto.message.BasicRequest;
import com.example.demo.dto.message.BasicResponse;
import com.example.demo.exception.ValidateException;
import com.example.demo.model.Partner;
import com.example.demo.model.Transaction;
import com.example.demo.repo.TransactionRepo;
import io.vavr.Function4;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.function.Function;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Log4j2
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final SignService signService;

    public Function<
            Function4<? super Partner, PublicKey, PrivateKey, Transaction, Try<? extends BasicResponse>>,
            ? extends BasicResponse>
    manageTransaction(BasicRequest request, TransactionType type) {
        return process -> signService.validateRequestSignAndFinallyCreateEncryptedSignForResponse(request)
                .apply((partner, publicKey, privateKey) -> type == TransactionType.CHECK_TRANSACTION ?
                        getTransaction(request)
                                .flatMap(transaction -> process.apply(
                                        partner, publicKey,
                                        privateKey, transaction
                                        )
                                )
                        :
                        createTransaction(request, type, partner)
                                .flatMap(transaction -> resolveTransaction(transaction,
                                                process.apply(
                                                        partner, publicKey,
                                                        privateKey, transaction
                                                )
                                        )
                                )
                );
    }

    private Try<Transaction> createTransaction(
            BasicRequest request,
            TransactionType type,
            Partner partner) {
        return Try.of(() -> transactionRepo.save(new Transaction()
                .setPartnerCode(partner.getPartnerCode())
                .setTransId(request.optionPartnerTransId().getOrElse(""))
                .setType(type.CODE)
                .setStatus(TransactionStatus.PROCESSING.CODE)
        ));
    }

    private Try<Transaction> getTransaction(BasicRequest request) {
        return request
                .optionPartnerTransId()
                .flatMap(transactionRepo::findByTransId)
                .fold(
                        () -> Try.failure(new ValidateException("Transaction not found")),
                        Try::success
                );
    }

    private <U> Try<U> resolveTransaction(Transaction transaction, Try<U> processResult) {
        if (processResult.isSuccess()) completeTransaction(transaction);
        else resolveFailingTransaction(transaction);
        return processResult;
    }

    private void completeTransaction(Transaction transaction) {
        transaction.setStatus(TransactionStatus.SUCCESS.CODE);
        transactionRepo.save(transaction);
    }

    private void resolveFailingTransaction(Transaction transaction) {
        transaction.setStatus(TransactionStatus.FAILED.CODE);
        transactionRepo.save(transaction);
    }

}
