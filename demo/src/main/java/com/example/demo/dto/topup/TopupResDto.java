package com.example.demo.dto.topup;

import com.example.demo.dto.message.BasicResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class TopupResDto extends BasicResponseDto implements DirectTopupRes {
    private long discountValue;
    private long debitValue;
}
