package com.kharzixen.cartservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDtoOutDetailed {
    private String id;
    private String productId;
    private int quantity;
    private CartDtoOutNoItems cart;
}
