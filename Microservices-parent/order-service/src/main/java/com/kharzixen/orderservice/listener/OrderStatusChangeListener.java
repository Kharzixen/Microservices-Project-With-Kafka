package com.kharzixen.orderservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kharzixen.orderservice.error_handling.error_messages.ErrorResponse;
import com.kharzixen.orderservice.error_handling.exceptions.InsufficientInventoryException;
import com.kharzixen.orderservice.error_handling.exceptions.OrderNotFoundException;
import com.kharzixen.orderservice.event.OrderStatusChangeEvent;
import com.kharzixen.orderservice.model.Order;
import com.kharzixen.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class OrderStatusChangeListener {

    private final ObjectMapper objectMapper;
    private final OrderService orderService;
    @KafkaListener(topics = "orderStatusTopic")
    public void handleStatusChanges(String message) {
        try {
            OrderStatusChangeEvent orderStatusChangeEvent =objectMapper.readValue(message, OrderStatusChangeEvent.class);
            Optional<Order> newOrder = orderService.updateOrderStatus(orderStatusChangeEvent.getOrderId(),
                    orderStatusChangeEvent.getStatus());
            if(newOrder.isEmpty()){
                throw new OrderNotFoundException(orderStatusChangeEvent.getOrderId());
            } else {
                log.info("Order status changed: " + orderStatusChangeEvent.getOrderId() + " - " +
                        orderStatusChangeEvent.getStatus());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public void handleOrderNotFoundException(OrderNotFoundException ex){
        log.info("404 Order not found. Exception: " + ex.getMessage());
    }

}
