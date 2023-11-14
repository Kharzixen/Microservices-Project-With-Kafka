package com.kharzixen.ordervalidationservice.dto;

import com.kharzixen.ordervalidationservice.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private UUID id;
    private String userId;
    private ZonedDateTime creationDate;
    private String status;
    private List<OrderItemDto> OrderItemList = new ArrayList<>();
}