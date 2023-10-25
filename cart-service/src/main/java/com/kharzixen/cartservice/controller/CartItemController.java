package com.kharzixen.cartservice.controller;

import com.kharzixen.cartservice.dto.CartDtoOut;
import com.kharzixen.cartservice.dto.ItemDtoIn;
import com.kharzixen.cartservice.dto.ItemDtoOut;
import com.kharzixen.cartservice.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/carts")
public class CartItemController {

    private final ItemService itemService;
    @PostMapping("/{id}/items")
    public ItemDtoOut createItemInCart(@PathVariable("id") String cartId, @RequestBody ItemDtoIn cartItemDtoIn){
        return itemService.createItemInCart(cartId, cartItemDtoIn);
    }
}
