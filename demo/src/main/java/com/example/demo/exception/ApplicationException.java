package com.example.demo.exception;

public class ApplicationException extends Exception{
    public ApplicationException(String errorMessage){
        super(errorMessage);
    }
}
