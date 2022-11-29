package com.example.demo.dto.card;

import com.example.demo.dto.message.BasicRequestDto;
import lombok.*;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Getter(onMethod_={@Deprecated, @Override})
@Setter
@ToString
@Accessors(chain = true)
@RequiredArgsConstructor
public class CardReqDto extends BasicRequestDto implements BuyCardReq {
    private String productCode;
    private Integer quantity;
    private String receiver = "";
}
