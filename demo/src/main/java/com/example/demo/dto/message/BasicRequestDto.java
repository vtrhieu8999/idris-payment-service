package com.example.demo.dto.message;

import lombok.*;
import lombok.experimental.Accessors;

@Getter(onMethod_={@Deprecated})
@Setter
@Accessors(chain = true)
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public class BasicRequestDto implements BasicRequest{
    private String partnerCode;
    private String partnerTransId;
    private String sign;
}
