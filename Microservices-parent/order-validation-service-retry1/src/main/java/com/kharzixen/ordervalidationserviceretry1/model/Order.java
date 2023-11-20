package com.kharzixen.ordervalidationserviceretry1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("Orders")
public class Order {
    @Id
    private UUID id;
    private String userId;
    private Date creationDate;
    private String status;
    @Builder.Default
    private List<OrderItem> orderItemList = new ArrayList<>();

    @Builder.Default
    private int validationTries = 0;
}
