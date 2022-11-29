package com.example.demo.config.constant;

import com.example.demo.dto.message.BasicResponse;
import com.example.demo.dto.message.BasicResponseDto;
import com.example.demo.exception.NotEnoughCardException;
import com.example.demo.exception.NotEnoughMoneyException;
import com.example.demo.exception.PartnerNotExistException;
import com.example.demo.exception.ProductNotExistException;

import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
public interface ErrorHandler<E extends Exception> extends Function<E, BasicResponse> {
    ErrorHandler<PartnerNotExistException> INCORRECT_PARTNER_CODE = errorCode("01");
    ErrorHandler<ProductNotExistException> INCORRECT_PRODUCT_CODE = errorCode("04");
    ErrorHandler<NotEnoughMoneyException> NOT_ENOUGH_BALANCE = errorCode("06");
    ErrorHandler<NotEnoughCardException> NOT_ENOUGH_CARD = errorCode("07");

    static <T extends BasicResponse> T catching(Throwable throwable){
        return null;
    }



    String getErrorCode();
    @Override
    default BasicResponse apply(E e) {
        return new BasicResponseDto()
                .setResMessage(e.getMessage())
                .setResCode(getErrorCode());
    }
    default Function<E, BasicResponse> peek(Consumer<? super E> pk){
        return e -> {
            pk.accept(e);
            return apply(e);
        };
    }

    static <T extends Exception> ErrorHandler<T> errorCode(String errorCode){
        return () -> errorCode;
    }
}
