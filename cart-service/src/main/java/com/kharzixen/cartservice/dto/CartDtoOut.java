package com.kharzixen.cartservice.dto;

import com.kharzixen.cartservice.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDtoOut {
    private String id;
    private String userId;

    private List<Item> itemList;
}
