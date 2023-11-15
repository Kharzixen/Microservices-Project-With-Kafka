package com.kharzixen.ordervalidationservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kharzixen.ordervalidationservice.dto.OrderDto;
import com.kharzixen.ordervalidationservice.error_handling.exception.ClientConnectionException;
import com.kharzixen.ordervalidationservice.error_handling.exception.OrderInvalidException;
import com.kharzixen.ordervalidationservice.event.OrderNotificationEvent;
import com.kharzixen.ordervalidationservice.event.OrderStatusChangeEvent;
import com.kharzixen.ordervalidationservice.event.OrderValidationEvent;
import com.kharzixen.ordervalidationservice.model.Order;
import com.kharzixen.ordervalidationservice.service.OrderValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@AllArgsConstructor
@Slf4j
public class OrderPlacedListener {
    private final OrderValidationService orderValidationService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "orderPlacedTopic")
    public void handleNotification(String message) {
        try {
            OrderValidationEvent orderValidationEvent = objectMapper.readValue(message, OrderValidationEvent.class);
            log.info("Received Order for validation - {}", orderValidationEvent.getOrderDto().getId());
            if (orderValidationService.isValidInventory(orderValidationEvent.getOrderDto())) {
                log.info("Received order is valid");
                OrderStatusChangeEvent orderStatusChangeEvent = new OrderStatusChangeEvent(
                        orderValidationEvent.getOrderDto().getId(), "validated");
                kafkaTemplate.send("orderStatusTopic", objectMapper.writeValueAsString(orderStatusChangeEvent));
            } else {
                OrderStatusChangeEvent orderStatusChangeEvent = new OrderStatusChangeEvent(
                        orderValidationEvent.getOrderDto().getId(), "refused - no inventory");
                kafkaTemplate.send("orderStatusTopic", objectMapper.writeValueAsString(orderStatusChangeEvent));
            }
        } catch (OrderInvalidException e){
            log.info(e.getMessage());
        } catch (ClientConnectionException e){
            log.info("Client service unavailable: " + e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
