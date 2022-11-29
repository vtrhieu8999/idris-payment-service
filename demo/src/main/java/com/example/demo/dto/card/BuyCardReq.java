package com.example.demo.dto.card;

import com.example.demo.dto.message.BasicRequest;
import io.vavr.control.Option;

import java.util.Objects;

@SuppressWarnings("DeprecatedIsStillUsed")
public interface BuyCardReq extends BasicRequest {
    @Deprecated
    String getProductCode();
    @Deprecated
    Integer getQuantity();
    @Deprecated
    String getReceiver();

    default Option<String> optionProductCode() {return Option.of(getProductCode());}
    default Option<Integer> optionQuantity() {return Option.of(getQuantity());}
    default Option<String> optionReceiver() {return Option.of(getReceiver());}

    @Override
    default StringBuilder signBody(StringBuilder current) {
        return BasicRequest.super.signBody(current)
                .append(optionProductCode().getOrElse(""))
                .append(optionReceiver().getOrElse(""));
    }
}
