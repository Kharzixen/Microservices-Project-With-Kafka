package com.kharzixen.cartservice.controller;

import com.kharzixen.cartservice.dto.CartDtoIn;
import com.kharzixen.cartservice.dto.CartDtoOut;
import com.kharzixen.cartservice.model.Cart;
import com.kharzixen.cartservice.service.CartService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
@AllArgsConstructor
@Log
public class CartController {

    private final CartService cartService;

    @PostMapping
    public CartDtoOut createCart(@RequestBody CartDtoIn cartDtoIn){
        log.severe("asd");
        return cartService.createCart(cartDtoIn);
    }
}
