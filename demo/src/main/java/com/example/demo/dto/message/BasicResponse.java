package com.example.demo.dto.message;

public interface BasicResponse extends BasicRequest {
    String getResCode();
    String getResMessage();
    BasicResponse setSign(String sign);

    @Override
    default StringBuilder signHeader(StringBuilder current) {
        return BasicRequest.super.signHeader(
                current.append(getResCode())
                        .append(getResMessage())
        );
    }
}
