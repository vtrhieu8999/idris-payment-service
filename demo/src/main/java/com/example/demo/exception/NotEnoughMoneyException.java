package com.example.demo.exception;

public class NotEnoughMoneyException extends ApplicationException{
    public NotEnoughMoneyException(String errorMessage){
        super(errorMessage);
    }
}
