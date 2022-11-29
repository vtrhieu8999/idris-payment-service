package com.example.demo.controller;

import com.example.demo.repo.BillRepo;
import com.example.demo.repo.PartnerRepo;
import com.example.demo.service.EncryptService;
import com.example.demo.service.TransactionService;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.function.*;

@RestController
@RequestMapping("bill")
@AllArgsConstructor(onConstructor_={@Autowired})
public class BillController {
    private final EncryptService encryptService;
    private final TransactionService transactionService;
    private final BillRepo billRepo;
    private final PartnerRepo partnerRepo;

    @PostMapping("queryBill")
    public ResponseEntity<Map> queryBill(Map<String, String> json){
        //TODO: write logic
        return null;
    }

    @PostMapping("payBill")
    public ResponseEntity<Map> payBill(Map<String, String> json){
        //TODO: write logic
        return null;
    }

    @FunctionalInterface
    public interface CheckedSupplier<E extends Exception, T> extends Supplier<T>{
        T checkedGet() throws E;

        @Override
        @SneakyThrows
        default T get(){
            return checkedGet();
        }

        @SuppressWarnings("unchecked")
        default Either<E, T> toEither(){
            try {
                return Either.right(get());
            }catch (Exception e){
                return Either.left((E) e);
            }
        }

        default Try<T> toTry(){return Try.of(this::checkedGet);}

        static <U extends Exception, R> Supplier<R> of(CheckedSupplier<U, R> methodReference){return methodReference;}
    }
}
