package com.example.demo.exception;

public class ProductNotExistException extends ApplicationException{
    public ProductNotExistException(String errorMessage){
        super(errorMessage);
    }
}
