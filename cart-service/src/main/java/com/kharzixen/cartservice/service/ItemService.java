package com.kharzixen.cartservice.service;

import com.kharzixen.cartservice.dto.CartDtoOut;
import com.kharzixen.cartservice.dto.ItemDtoIn;
import com.kharzixen.cartservice.dto.ItemDtoOut;
import com.kharzixen.cartservice.mapper.CartMapper;
import com.kharzixen.cartservice.mapper.ItemMapper;
import com.kharzixen.cartservice.model.Cart;
import com.kharzixen.cartservice.model.Item;
import com.kharzixen.cartservice.repository.CartRepository;
import com.kharzixen.cartservice.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    public ItemDtoOut createItemInCart(String cartId, ItemDtoIn itemDtoIn){
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if(optionalCart.isPresent()){
            Cart cart = optionalCart.get();
            if(cart.getItemList() == null){
                cart.setItemList(new ArrayList<>());
            }
            Item item = ItemMapper.INSTANCE.modelFromDto(itemDtoIn);
            item.setCart(cart);
            itemRepository.save(item);
            cart.getItemList().add(item);
            return ItemMapper.INSTANCE.modelToDto(item);
        } else {
            return null;
        }
    }
}
