package com.example.demo.exception;

public class DuplicateTransactionException extends ApplicationException{
    public DuplicateTransactionException(String errorMessage) {
        super(errorMessage);
    }
}
