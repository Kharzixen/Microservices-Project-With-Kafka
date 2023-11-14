package com.kharzixen.notificationservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kharzixen.notificationservice.event.OrderNotificationEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class NotificationListener {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(String message) {
        OrderNotificationEvent orderNotificationEvent = null;
        try {
            orderNotificationEvent = objectMapper.readValue(message, OrderNotificationEvent.class);
            // send out an email notification
            // status -> different email for example
            log.info("Received Notification for Order: " + orderNotificationEvent.getOrderId() + " - status: " + orderNotificationEvent.getStatus());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
