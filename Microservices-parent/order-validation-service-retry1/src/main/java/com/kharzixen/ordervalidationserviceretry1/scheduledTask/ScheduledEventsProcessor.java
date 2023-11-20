package com.kharzixen.ordervalidationserviceretry1.scheduledTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kharzixen.ordervalidationserviceretry1.errorHandling.exception.ClientConnectionException;
import com.kharzixen.ordervalidationserviceretry1.errorHandling.exception.OrderInvalidException;
import com.kharzixen.ordervalidationserviceretry1.event.OrderStatusChangeEvent;
import com.kharzixen.ordervalidationserviceretry1.model.Order;
import com.kharzixen.ordervalidationserviceretry1.service.OrderService;
import com.kharzixen.ordervalidationserviceretry1.service.OrderValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Component
public class ScheduledEventsProcessor {

    private final OrderService orderService;
    private final OrderValidationService orderValidationService;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 60000)
    public void scheduledEventProcessorTask() {
        try {
            List<Order> orderList = orderService.getAllOrders();
            for (Order order : orderList) {
                try {
                    boolean isValid = orderValidationService.isValidInventory(order);
                    OrderStatusChangeEvent orderStatusChangeEvent;
                    if (isValid) {
                        orderStatusChangeEvent = new OrderStatusChangeEvent(
                                order.getId(), "validated");
                    } else {
                        orderStatusChangeEvent = new OrderStatusChangeEvent(
                                order.getId(), "refused - no inventory");
                    }
                    kafkaTemplate.send("orderStatusTopic", objectMapper.writeValueAsString(orderStatusChangeEvent));
                    orderService.delete(order.getId());
                    log.info("Order successfully validated: Order with id:{} has status: {}", order.getId(),orderStatusChangeEvent.getStatus());
                } catch (OrderInvalidException e) {
                    log.info(e.getMessage());
                    OrderStatusChangeEvent orderStatusChangeEvent = new OrderStatusChangeEvent(
                            order.getId(), "refused - invalid product in order");
                    kafkaTemplate.send("orderStatusTopic", objectMapper.writeValueAsString(orderStatusChangeEvent));
                    orderService.delete(order.getId());
                    log.error("Order validation unsuccessful: Order with id:{} has status: {}", order.getId(),orderStatusChangeEvent.getStatus());
                } catch (ClientConnectionException e) {
                    log.info("Client service unavailable: " + e.getMessage());

                    if(order.getValidationTries() == 15){
                        //send to dead letter
                        log.info("Order with id: {} verified failed too many times." +
                                " Moving to dead letter queue.", order.getId());
                        orderService.delete(order.getId());
                    } else {
                        order.setValidationTries(order.getValidationTries() + 1);
                        orderService.saveOrder(order);
                        log.info("Order with id: {} verification failed" +
                                " Verification will be retried.", order.getId());

                    }
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}


