package com.kharzixen.ordervalidationserviceretry1.event;

import com.kharzixen.ordervalidationserviceretry1.dto.OrderDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderValidationEvent {
    private OrderDto orderDto;
    private ZonedDateTime eventPublishedDate;
}
