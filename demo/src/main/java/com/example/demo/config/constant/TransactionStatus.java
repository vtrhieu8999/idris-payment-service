package com.example.demo.config.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TransactionStatus {
    SUCCESS(0),
    PROCESSING(1),
    FAILED(2);
    public final int CODE;
}
