package com.example.demo.dto.transaction;

import com.example.demo.dto.message.BasicResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TransactionResDto extends BasicResponseDto implements TransactionRes {
    int status;
}
