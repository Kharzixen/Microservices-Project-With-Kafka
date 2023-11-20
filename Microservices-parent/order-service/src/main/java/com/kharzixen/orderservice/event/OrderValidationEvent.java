package com.kharzixen.orderservice.event;

import com.kharzixen.orderservice.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderValidationEvent {
    private OrderDto orderDto;
    private Date eventPublishedDate;
}