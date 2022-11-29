package com.example.demo.dto.transaction;

import com.example.demo.dto.message.BasicRequestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransactionReqDto extends BasicRequestDto implements TransactionReq {
    String transType;
}
