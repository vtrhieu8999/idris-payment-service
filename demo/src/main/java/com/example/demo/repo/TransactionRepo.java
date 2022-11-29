package com.example.demo.repo;

import com.example.demo.model.Transaction;
import io.vavr.control.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
    Option<Transaction> findByTransId(String transId);
}
