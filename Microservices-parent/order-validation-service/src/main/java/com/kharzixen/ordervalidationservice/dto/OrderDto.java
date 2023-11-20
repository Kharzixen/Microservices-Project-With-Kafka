package com.kharzixen.ordervalidationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private UUID id;
    private String userId;
    private Date creationDate;
    private String status;
    private List<OrderItemDto> OrderItemList = new ArrayList<>();
}