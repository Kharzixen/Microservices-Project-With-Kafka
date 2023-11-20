package com.kharzixen.orderservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderNotificationEvent {
    private UUID orderId;
    private String userId;
    private Date creationDate;
    private String status;
}
