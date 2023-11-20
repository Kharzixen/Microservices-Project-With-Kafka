package com.kharzixen.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDtoOut {
    private String id;

    private String name;

    private String description;

    private BigDecimal price;

    private int inventory;

}
