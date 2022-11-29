package com.example.demo.service;

import com.example.demo.config.constant.ErrorHandler;
import com.example.demo.dto.message.BasicRequest;
import com.example.demo.dto.message.BasicResponse;
import com.example.demo.exception.ValidateException;
import com.example.demo.model.Partner;
import io.vavr.Function3;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.function.Function;

@Service
@Log4j2
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class SignService {
    private final EncryptService encryptService;
    private final PartnerService partnerService;

    private boolean isValidSign(PrivateKey privateKey, BasicRequest request) {
        return request.optionSign().map(
                encryptedSign -> encryptService
                        .decryptMessage(privateKey, encryptedSign)
                        .map(request::compareSign)
                        .getOrElse(false)
        ).getOrElse(false);
    }

    <T extends BasicResponse> Function<
            Function3<Partner, PublicKey, PrivateKey, Try<T>>,
            ? extends BasicResponse>
    validateRequestSignAndFinallyCreateEncryptedSignForResponse(BasicRequest request) {
        return process -> partnerService
                .findPartnerByRequest(request)
                .flatMap(partner -> encryptService
                        .convertStringToPublicKey(partner.getPublicKey())
                        .flatMap(publicKey -> encryptService
                                .convertStringToPrivateKey(partner.getPrivateKey())
                                .flatMap(privateKey -> isValidSign(privateKey, request) ?
                                        process.apply(partner, publicKey, privateKey)
                                        :
                                        Try.failure(new ValidateException("Cannot verify sign"))
                                )
                                .flatMap(response -> encryptService.encryptMessage(
                                                        publicKey,
                                                        response.buildSign()
                                                )
                                                .map(response::setSign)
                                )
                        )
                )
                .fold(ErrorHandler::catching, Function.identity());
    }

}
