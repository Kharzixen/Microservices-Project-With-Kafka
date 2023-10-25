package com.kharzixen.cartservice.service;

import com.kharzixen.cartservice.dto.CartDtoIn;
import com.kharzixen.cartservice.dto.CartDtoOut;
import com.kharzixen.cartservice.mapper.CartMapper;
import com.kharzixen.cartservice.model.Cart;
import com.kharzixen.cartservice.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;


    public CartDtoOut createCart(CartDtoIn cartDtoIn){
        Cart cart = CartMapper.INSTANCE.modelFromDto(cartDtoIn);
        Cart savedCart = cartRepository.save(cart);
        return CartMapper.INSTANCE.modelToDto(savedCart);
    }
}
