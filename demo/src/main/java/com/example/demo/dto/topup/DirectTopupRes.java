package com.example.demo.dto.topup;

import com.example.demo.dto.message.BasicResponse;

public interface DirectTopupRes extends BasicResponse {
    long getDiscountValue();
    long getDebitValue();

    @Override
    default StringBuilder signBody(StringBuilder current) {
        return BasicResponse.super.signBody(current)
                .append(getDiscountValue())
                .append(getDebitValue());
    }
}
