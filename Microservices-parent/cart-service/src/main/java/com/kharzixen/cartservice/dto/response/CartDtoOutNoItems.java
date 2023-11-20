package com.kharzixen.cartservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDtoOutNoItems {
    private String id;
    private String userId;
    private String userEmail;
}
