package com.kharzixen.orderservice.dto;

import com.kharzixen.orderservice.model.OrderItem;
import jakarta.persistence.*;
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
public class OrderDto {

    private UUID id;
    private String userId;
    private Date creationDate;
    private String status;

    private List<OrderItemDto> orderItemList;
}
