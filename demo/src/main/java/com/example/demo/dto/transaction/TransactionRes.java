package com.example.demo.dto.transaction;

import com.example.demo.dto.message.BasicResponse;

public interface TransactionRes extends BasicResponse {
    int getStatus();

    @Override
    default StringBuilder signBody(StringBuilder current) {
        return BasicResponse.super
                .signBody(current)
                .append(getStatus());
    }
}
