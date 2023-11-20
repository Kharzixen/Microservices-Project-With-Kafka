package com.kharzixen.ordervalidationserviceretry1.repository;

import com.kharzixen.ordervalidationserviceretry1.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface OrderRepository extends MongoRepository<Order, UUID> {
}
