package com.example.demo.dto.topup;

import com.example.demo.dto.message.BasicRequest;
import io.vavr.control.Option;

import java.util.Objects;

@SuppressWarnings("DeprecatedIsStillUsed")
public interface DirectTopupReq extends BasicRequest {
    @Deprecated
    String getTelcoCode();
    @Deprecated
    Long getTopupAmount();
    @Deprecated
    Integer getMobileNo();

    default Option<String> optionTelcoCode() {return Option.of(getTelcoCode());}
    default Option<Long> optionTopupAmount() {return Option.of(getTopupAmount());}
    default Option<Integer> optionMobileNo() {return Option.of(getMobileNo());}

    @Override
    default StringBuilder signBody(StringBuilder current) {
        return BasicRequest.super.signBody(current)
                .append(optionTelcoCode().getOrElse(""))
                .append(optionMobileNo().fold(BasicRequest::blank, Objects::toString))
                .append(optionTopupAmount().fold(BasicRequest::blank, Objects::toString));
    }
}
