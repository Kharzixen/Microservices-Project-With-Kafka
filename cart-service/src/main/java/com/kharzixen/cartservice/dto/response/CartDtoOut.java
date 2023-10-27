package com.kharzixen.cartservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDtoOut {
    private String id;
    private String userId;

    private List<ItemDtoOut> itemList;
}
