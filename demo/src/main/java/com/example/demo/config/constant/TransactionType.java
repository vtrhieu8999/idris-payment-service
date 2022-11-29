package com.example.demo.config.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TransactionType {
    TOP_UP("TU"),
    BUY_CARD("BC"),
    PAY_BILL("PB"),
    CHECK_TRANSACTION("CK");
    public final String CODE;
}
