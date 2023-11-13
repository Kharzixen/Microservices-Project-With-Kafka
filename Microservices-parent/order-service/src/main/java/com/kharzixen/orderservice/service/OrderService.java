package com.kharzixen.orderservice.service;

import com.kharzixen.orderservice.Dto.CartDto;
import com.kharzixen.orderservice.Dto.CartItemDto;
import com.kharzixen.orderservice.Dto.InventoryDto;
import com.kharzixen.orderservice.client.CartClient;
import com.kharzixen.orderservice.client.InventoryClient;
import com.kharzixen.orderservice.error_handling.exceptions.InsufficientInventoryException;
import com.kharzixen.orderservice.error_handling.exceptions.OrderBadRequestException;
import com.kharzixen.orderservice.event.OrderNotificationEvent;
import com.kharzixen.orderservice.event.OrderValidationEvent;
import com.kharzixen.orderservice.model.Order;
import com.kharzixen.orderservice.model.OrderItem;
import com.kharzixen.orderservice.repository.OrderItemRepository;
import com.kharzixen.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@Log
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartClient cartClient;
    private final InventoryClient inventoryClient;

    private final KafkaTemplate<String, OrderNotificationEvent> notificationTemplate;
    private final KafkaTemplate<String, OrderValidationEvent> orderValidationTemplate;


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
        // Map<String, Integer> inventoryMap = getInventoryMap(inventoryClient.getInventoryByProductId(itemIdList));

        //List<String> lowQuantityItems = validateInventory(inventoryMap, cart);

        log.severe("ORDER PLACED AS PENDING");
        Order order = convertCartToOrder(cart);
        try {
            notificationTemplate.send("notificationTopic", new OrderNotificationEvent(
                    order.getId(), order.getUserId(), ZonedDateTime.now(), order.getStatus())
            );
            orderValidationTemplate.send("orderPlacedTopic", new OrderValidationEvent(
                    order, "validation", ZonedDateTime.now()
            ) );

        } catch (KafkaProducerException e){
            //temporary solution
            throw new OrderBadRequestException(e.getMessage());
        }
        return "ORDER PLACED AS PENDING";
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

    private static Order convertCartToOrder(CartDto cart){
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setStatus("pending");
        order.setUserId(cart.getUserId());
        order.setCreationDate(ZonedDateTime.now());
        List<OrderItem> orderItemList = new ArrayList<>();
        long i = 0;
        for(CartItemDto cartItemDto:cart.getItemList()){
            OrderItem orderItem = new OrderItem();
            orderItem.setId(i);
            i++;
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setProductId(cartItemDto.getProductId());
            orderItemList.add(orderItem);
        }
        order.setOrderItemList(orderItemList);
        return order;
    }
}
