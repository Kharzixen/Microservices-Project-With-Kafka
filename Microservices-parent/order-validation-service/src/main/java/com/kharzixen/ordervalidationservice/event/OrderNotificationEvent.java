package com.kharzixen.ordervalidationservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderNotificationEvent {
    private UUID orderId;
    private String userId;
    private ZonedDateTime creationDate;
    private String message;
}
