package com.kharzixen.cartservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kharzixen.cartservice.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDtoOut {
    private String id;
    private String productId;
    private int quantity;
    @JsonIgnore
    private Cart cart;
}
