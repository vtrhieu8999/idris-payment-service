package com.example.demo.repo;

import com.example.demo.model.product.CardDetails;
import io.vavr.control.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardConfigRepo extends JpaRepository<CardDetails, Long> {
    Option<CardDetails> findCardConfigByPriceTagAndTelcoCode(int priceTag, String telcoCode);
}
