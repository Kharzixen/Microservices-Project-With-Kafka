package com.kharzixen.cartservice.service;

import com.kharzixen.cartservice.dto.request.CartDtoIn;
import com.kharzixen.cartservice.dto.response.CartDtoOut;
import com.kharzixen.cartservice.error_handling.exceptions.CartAlreadyExistsException;
import com.kharzixen.cartservice.error_handling.exceptions.CartNotFoundException;
import com.kharzixen.cartservice.mapper.CartMapper;
import com.kharzixen.cartservice.model.Cart;
import com.kharzixen.cartservice.repository.CartRepository;
import com.kharzixen.cartservice.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;


    public CartDtoOut createCart(CartDtoIn cartDtoIn) throws CartAlreadyExistsException {
        try {
            Cart cart = CartMapper.INSTANCE.modelFromDto(cartDtoIn);
            Cart savedCart = cartRepository.save(cart);
            return CartMapper.INSTANCE.modelToDto(savedCart);
        } catch (DuplicateKeyException ex){
            throw new CartAlreadyExistsException(cartDtoIn.getUserId());
        }
    }

    public List<CartDtoOut>  getAllCarts(){
        List<Cart> carts = (List<Cart>) cartRepository.findAll();
        return carts.stream().map(CartMapper.INSTANCE::modelToDto).toList();
    }

    public CartDtoOut getCartByUser(String userId) throws CartNotFoundException {
        Optional<Cart> optionalCart = cartRepository.getCartByUserId(userId);
        if(optionalCart.isPresent()){
            return CartMapper.INSTANCE.modelToDto(optionalCart.get());
        } else {
            throw new CartNotFoundException("userId", userId);
        }
    }

    public CartDtoOut getCartById(String cartId) throws CartNotFoundException {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if(optionalCart.isPresent()){
            return CartMapper.INSTANCE.modelToDto(optionalCart.get());
        } else {
            throw new CartNotFoundException("cartId", cartId);
        }
    }

    public void deleteCart(String cartId) throws CartNotFoundException {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if(optionalCart.isPresent()){
            Cart cart = optionalCart.get();
            itemRepository.deleteByCart(optionalCart.get());
            cartRepository.deleteById(cartId);
        } else {
            throw new CartNotFoundException("cartId", cartId);
        }

    }
}
