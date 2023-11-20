package com.kharzixen.ordervalidationserviceretry1.dto;

import com.kharzixen.ordervalidationserviceretry1.dto.OrderItemDto;
import com.kharzixen.ordervalidationserviceretry1.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
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
    private List<OrderItemDto> orderItemList = new ArrayList<>();
}