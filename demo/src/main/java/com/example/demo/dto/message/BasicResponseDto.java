package com.example.demo.dto.message;

import lombok.*;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class BasicResponseDto extends BasicRequestDto implements BasicResponse{
    private String resCode;
    private String resMessage;

    @Override
    public BasicResponseDto setSign(String sign){
        super.setSign(sign);
        return this;
    }
}
