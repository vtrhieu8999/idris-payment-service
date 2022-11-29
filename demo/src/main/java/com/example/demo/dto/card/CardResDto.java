package com.example.demo.dto.card;

import com.example.demo.dto.topup.TopupResDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class CardResDto extends TopupResDto implements BuyCardRes {
    private long totalValue;
    private List<CardInfo> cardList;
}
