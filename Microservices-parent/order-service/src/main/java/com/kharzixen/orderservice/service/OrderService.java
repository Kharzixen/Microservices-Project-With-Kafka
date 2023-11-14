package com.kharzixen.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kharzixen.orderservice.dto.CartDto;
import com.kharzixen.orderservice.dto.CartItemDto;
import com.kharzixen.orderservice.dto.InventoryDto;
import com.kharzixen.orderservice.client.CartClient;
import com.kharzixen.orderservice.error_handling.exceptions.OrderBadRequestException;
import com.kharzixen.orderservice.event.OrderNotificationEvent;
import com.kharzixen.orderservice.event.OrderValidationEvent;
import com.kharzixen.orderservice.mapper.OrderMapper;
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

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;



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
        Order savedOrder = orderRepository.save(order);

        try {
            OrderNotificationEvent orderNotificationEvent = new OrderNotificationEvent(
                    savedOrder.getId(), savedOrder.getUserId(), savedOrder.getCreationDate(), savedOrder.getStatus());
            kafkaTemplate.send("notificationTopic", objectMapper.writeValueAsString(orderNotificationEvent));
            OrderValidationEvent orderValidationEvent = new OrderValidationEvent(
                    OrderMapper.INSTANCE.modelToDto(savedOrder),  ZonedDateTime.now()
            );
            kafkaTemplate.send("orderPlacedTopic",objectMapper.writeValueAsString(orderValidationEvent));


        } catch (KafkaProducerException e){
            //temporary solution
            throw new OrderBadRequestException(e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "ORDER PLACED AS PENDING";
    }

    @Transactional
    public Optional<Order> updateOrderStatus(UUID orderId, String status){
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isPresent()){
            Order newOrder = order.get();
            newOrder.setStatus(status);
            return Optional.of(orderRepository.save(newOrder));
        } else {
            return Optional.empty();
        }
    }

    private static Order convertCartToOrder(CartDto cart){
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setStatus("pending");
        order.setUserId(cart.getUserId());
        order.setCreationDate(ZonedDateTime.now());
        List<OrderItem> orderItemList = new ArrayList<>();

        for (CartItemDto cartItemDto : cart.getItemList()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setProductId(cartItemDto.getProductId());
            orderItem.setOrder(order);
            orderItemList.add(orderItem);
        }

        // Set OrderItems to the Order
        order.setOrderItemList(orderItemList);

        return order;
    }
}
