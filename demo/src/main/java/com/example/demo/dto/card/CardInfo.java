package com.example.demo.dto.card;

import com.example.demo.model.product.Card;
import lombok.Data;

import java.util.Date;

@Data
public class CardInfo {
    private String serial;
    private String pincode;
    private Date expiredate;

    public CardInfo() {
    }

    public CardInfo(Card card){
        this.serial = card.getSerial();
        this.pincode = card.getPinCode();
        this.expiredate = card.getExpireDate();
    }
}
