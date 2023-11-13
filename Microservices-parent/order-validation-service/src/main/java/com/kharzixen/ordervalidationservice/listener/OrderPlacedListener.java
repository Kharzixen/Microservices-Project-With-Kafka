package com.kharzixen.ordervalidationservice.listener;

import com.kharzixen.ordervalidationservice.event.OrderValidationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderPlacedListener {
    @KafkaListener(topics = "orderPlacedTopic")
    public void handleNotification(OrderValidationEvent orderPlacedEvent) {
        // send out an email notification
        log.info("Received Order for validation - {}", orderPlacedEvent.getOrder());
    }
}
