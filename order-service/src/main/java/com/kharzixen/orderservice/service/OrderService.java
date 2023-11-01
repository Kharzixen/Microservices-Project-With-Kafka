package com.kharzixen.orderservice.service;

import com.kharzixen.orderservice.repository.OrderItemRepository;
import com.kharzixen.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public void createOrder(String cartId){
        //call CartService for the cart content

        //call inventory service for the stock

        //place order as "pending"

        //fire the "do reservation event"
    }
}
