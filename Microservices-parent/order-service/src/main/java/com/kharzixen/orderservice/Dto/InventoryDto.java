package com.kharzixen.orderservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDto {
    Long id;
    String productId;
    String storageKeepingUnit;
    Integer quantity;
    BigDecimal sellingPrice;
}
