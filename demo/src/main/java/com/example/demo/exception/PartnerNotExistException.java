package com.example.demo.exception;

public class PartnerNotExistException extends ApplicationException{
    public PartnerNotExistException(String errorMessage){
        super(errorMessage);
    }
}
