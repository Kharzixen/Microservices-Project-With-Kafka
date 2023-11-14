package com.kharzixen.orderservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusChangeEvent {
    private UUID orderId;
    private String status;
}
