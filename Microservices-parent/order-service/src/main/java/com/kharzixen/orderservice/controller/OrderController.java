package com.kharzixen.orderservice.controller;

import com.kharzixen.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public void placeOrder(@RequestParam(name = "cartId", required = true) String cartId){
        orderService.createOrder(cartId);
    }
}
