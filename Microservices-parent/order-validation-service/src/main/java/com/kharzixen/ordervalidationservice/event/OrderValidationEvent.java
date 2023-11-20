package com.kharzixen.ordervalidationservice.event;

import com.kharzixen.ordervalidationservice.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderValidationEvent {
    private OrderDto orderDto;
    private Date eventPublishedDate;
}
