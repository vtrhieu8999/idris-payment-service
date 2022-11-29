package com.example.demo.service.factory;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class Content<T> {
    @SneakyThrows
    public StringBuilder fieldAccessor(Field field){
        Object result = field.get(this);
        if(result == null) return new StringBuilder("");
        else return new StringBuilder().append(result);
    }
}