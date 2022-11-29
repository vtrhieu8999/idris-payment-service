package com.example.demo.exception;

public class NotEnoughCardException extends ApplicationException{
    public NotEnoughCardException(String errorMessage){
        super(errorMessage);
    }
}
