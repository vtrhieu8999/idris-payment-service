package com.example.demo.dto.topup;

import com.example.demo.dto.message.BasicRequestDto;

import lombok.*;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Getter(onMethod_={@Deprecated})
@Setter
@ToString
@Accessors(chain = true)
@RequiredArgsConstructor
public class TopupReqDto extends BasicRequestDto implements DirectTopupReq {
    private String telcoCode;
    private Long topupAmount;
    private Integer mobileNo;
}
