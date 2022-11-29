package com.example.demo.dto.card;

import com.example.demo.dto.topup.DirectTopupRes;
import com.example.demo.model.product.Card;

import java.util.List;

public interface BuyCardRes extends DirectTopupRes {
    long getTotalValue();
    List<CardInfo> getCardList();

    @Override
    default StringBuilder signBody(StringBuilder current) {
        return DirectTopupRes.super.signBody(
                current.append(getTotalValue())
        ).append(getCardList());
    }
}
