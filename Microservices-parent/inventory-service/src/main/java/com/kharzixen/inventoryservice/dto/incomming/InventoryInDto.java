package com.kharzixen.inventoryservice.dto.incomming;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryInDto {
    String productId;
    String storageKeepingUnit;
    Integer quantity;
    BigDecimal sellingPrice;
}
