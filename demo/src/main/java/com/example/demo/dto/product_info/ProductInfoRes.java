package com.example.demo.dto.product_info;

import com.example.demo.dto.message.BasicResponse;

public interface ProductInfoRes extends BasicResponse {
    int getQuantity();

    @Override
    default StringBuilder signBody(StringBuilder current) {
        return BasicResponse.super
                .signBody(current)
                .append(getQuantity());
    }
}
