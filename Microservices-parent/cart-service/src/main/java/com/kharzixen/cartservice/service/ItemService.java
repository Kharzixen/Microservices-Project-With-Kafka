package com.kharzixen.cartservice.service;

import com.kharzixen.cartservice.dto.request.ItemDtoIn;
import com.kharzixen.cartservice.dto.response.ItemDtoOutDetailed;
import com.kharzixen.cartservice.error_handling.exceptions.BadQuantityException;
import com.kharzixen.cartservice.error_handling.exceptions.CartNotFoundException;
import com.kharzixen.cartservice.error_handling.exceptions.ItemInCartException;
import com.kharzixen.cartservice.error_handling.exceptions.ItemNotFoundException;
import com.kharzixen.cartservice.mapper.ItemMapper;
import com.kharzixen.cartservice.model.Cart;
import com.kharzixen.cartservice.model.Item;
import com.kharzixen.cartservice.repository.CartRepository;
import com.kharzixen.cartservice.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    public ItemDtoOutDetailed createItemInCart(String cartId, ItemDtoIn itemDtoIn) throws CartNotFoundException, ItemInCartException {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if(optionalCart.isPresent()){
            Cart cart = optionalCart.get();
            if(cart.getItemList() == null){
                cart.setItemList(new ArrayList<>());
            }
            Item item = ItemMapper.INSTANCE.modelFromDto(itemDtoIn);
            for(Item cartItem: cart.getItemList()){
                if(Objects.equals(cartItem.getProductId(), item.getProductId())){
                    throw new ItemInCartException(cart.getId(), item.getProductId(), cartItem.getId());
                }
            }
            item.setCart(cart);
            itemRepository.save(item);
            cart.getItemList().add(item);
            cartRepository.save(cart);
            return ItemMapper.INSTANCE.modelToDetailedDto(item);
        } else {
            throw new CartNotFoundException("cartId", cartId);
        }
    }

    public Long deleteAllItemsFromCart(String cartId) throws CartNotFoundException{
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if(optionalCart.isPresent()){
            Cart cart = optionalCart.get();
            Long countDeleted = itemRepository.deleteByCart(cart);
            cart.setItemList(new ArrayList<>());
            cartRepository.save(cart);
            return countDeleted;
        } else {
            throw new CartNotFoundException("cartId", cartId);
        }
    }

    public void deleteItemFromCart(String cartId, String itemId) throws CartNotFoundException, ItemNotFoundException{
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if(optionalCart.isPresent()){
            Cart cart = optionalCart.get();
            Optional<Item> optionalItem = itemRepository.findById(itemId);
            if(optionalItem.isEmpty()){
                throw new ItemNotFoundException(cartId, itemId);
            }

            itemRepository.deleteById(itemId);
            cart.getItemList().removeIf(item -> Objects.equals(item.getId(), itemId));
        } else {
            throw new CartNotFoundException("cartId", cartId);
        }
    }

    public ItemDtoOutDetailed patchItem(String itemId, ItemDtoIn itemDtoIn) throws BadQuantityException, ItemNotFoundException{
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if(optionalItem.isPresent()){
            Item item = optionalItem.get();
            Item patch = ItemMapper.INSTANCE.modelFromDto(itemDtoIn);
            if(patch.getQuantity() <=0 ){
                throw new BadQuantityException(itemId, patch.getQuantity());
            }

            item.setQuantity(patch.getQuantity());
            Item saved = itemRepository.save(item);
            return ItemMapper.INSTANCE.modelToDetailedDto(saved);
        } else {
            throw new ItemNotFoundException(itemId);
        }
    }
}
