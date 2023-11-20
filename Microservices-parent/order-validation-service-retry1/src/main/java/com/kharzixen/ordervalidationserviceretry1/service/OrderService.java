package com.kharzixen.ordervalidationserviceretry1.service;

import com.kharzixen.ordervalidationserviceretry1.model.Order;
import com.kharzixen.ordervalidationserviceretry1.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public void delete(UUID id){
        orderRepository.deleteById(id);
    }
}
