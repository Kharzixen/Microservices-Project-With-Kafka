package com.kharzixen.ordervalidationserviceretry1.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kharzixen.ordervalidationserviceretry1.errorHandling.exception.ClientConnectionException;
import com.kharzixen.ordervalidationserviceretry1.errorHandling.exception.OrderInvalidException;
import com.kharzixen.ordervalidationserviceretry1.event.OrderStatusChangeEvent;
import com.kharzixen.ordervalidationserviceretry1.event.OrderValidationEvent;
import com.kharzixen.ordervalidationserviceretry1.mapper.OrderMapper;
import com.kharzixen.ordervalidationserviceretry1.model.Order;
import com.kharzixen.ordervalidationserviceretry1.service.OrderService;
import com.kharzixen.ordervalidationserviceretry1.service.OrderValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class OrderPlacedListener {
    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "orderPlacedTopicRetry1")
    public void handleOrderValidationEvent(String message) {
        try {
            OrderValidationEvent orderValidationEvent = objectMapper.readValue(message, OrderValidationEvent.class);
            Order order = OrderMapper.INSTANCE.modelFromDto(orderValidationEvent.getOrderDto());
            Order saved = orderService.saveOrder(order);
            log.info("Received Order for validation - {}. Saved to database",saved);
        }  catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
