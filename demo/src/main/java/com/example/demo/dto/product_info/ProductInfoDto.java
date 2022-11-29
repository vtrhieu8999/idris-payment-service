package com.example.demo.dto.product_info;

import com.example.demo.dto.message.BasicResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductInfoDto extends BasicResponseDto implements ProductInfoRes {
    int quantity;
}
