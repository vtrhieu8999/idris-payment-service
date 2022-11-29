package com.example.demo.dto.transaction;

import com.example.demo.dto.message.BasicRequest;
import io.vavr.control.Option;

@SuppressWarnings("DeprecatedIsStillUsed")
public interface TransactionReq extends BasicRequest {
    @Deprecated
    String getTransType();
    default Option<String> optionTransType() {
        return Option.of(getTransType());
    }

    @Override
    default StringBuilder signBody(StringBuilder current) {
        return BasicRequest.super
                .signBody(current)
                .append(optionTransType().getOrElse(""));
    }
}
