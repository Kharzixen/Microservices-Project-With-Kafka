package com.kharzixen.orderservice.service;

import com.kharzixen.orderservice.Dto.CartDto;
import com.kharzixen.orderservice.Dto.CartItemDto;
import com.kharzixen.orderservice.Dto.InventoryDto;
import com.kharzixen.orderservice.client.CartClient;
import com.kharzixen.orderservice.client.InventoryClient;
import com.kharzixen.orderservice.error_handling.exceptions.InsufficientInventoryException;
import com.kharzixen.orderservice.error_handling.exceptions.OrderBadRequestException;
import com.kharzixen.orderservice.repository.OrderItemRepository;
import com.kharzixen.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Log
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartClient cartClient;
    private final InventoryClient inventoryClient;


    @Transactional
    public String createOrder(String cartId) {

        //catch errors, http status etc
        CartDto cart = cartClient.getCartById(cartId);

        //no id provided
        if (cart == null) {
            throw new OrderBadRequestException("Cart Id not provided, can not proceed");
        }
        //empty cart
        if (cart.getItemList() == null || cart.getItemList().isEmpty()) {
            throw new OrderBadRequestException("Cart with id: " + cart.getId() + " is empty, order can not be placed");
        }

        List<String> itemIdList = cart.getItemList().stream().map(CartItemDto::getProductId).toList();
        Map<String, Integer> inventoryMap = getInventoryMap(inventoryClient.getInventoryByProductId(itemIdList));

        List<String> lowQuantityItems = validateInventory(inventoryMap, cart);

        if (lowQuantityItems.isEmpty()) {
            log.severe("ORDER PLACED AS PENDING");
            return "ORDER PLACED AS PENDING";
        } else {
            log.severe("Cannot place order");
            throw new InsufficientInventoryException(lowQuantityItems);
        }

    }

    private static List<String> validateInventory(Map<String, Integer> inventoryMap, CartDto cart){
        List<String> lowQuantityItems = new ArrayList<>();
        for (CartItemDto cartItemDto : cart.getItemList()) {
            if (inventoryMap.get(cartItemDto.getProductId()) == null) {
                log.severe("Unsupported item with id: " + cartItemDto.getProductId() +
                        ", order is refused (inventory returned null)");
                throw new OrderBadRequestException("Unsupported item with id: " + cartItemDto.getProductId() +
                        ", order is refused (inventory returned null)");
            }
            if (cartItemDto.getQuantity() > inventoryMap.get(cartItemDto.getProductId())) {
                lowQuantityItems.add(cartItemDto.getProductId());
            }
        }
        return lowQuantityItems;
    }

    private static Map<String, Integer> getInventoryMap(List<InventoryDto> inventory){
        Map<String, Integer> inventoryMap = new HashMap<>();
        inventory.forEach(inventoryDto -> {
            inventoryMap.put(inventoryDto.getProductId(), inventoryDto.getQuantity());
        });
        return inventoryMap;
    }
}
