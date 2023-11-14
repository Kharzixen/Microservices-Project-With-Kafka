package com.kharzixen.ordervalidationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderItemDto {
    private Long id;
    private String productId;
    private int quantity;
}
