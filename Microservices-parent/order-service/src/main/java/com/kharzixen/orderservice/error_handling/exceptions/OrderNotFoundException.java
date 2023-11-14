package com.kharzixen.orderservice.error_handling.exceptions;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(UUID orderId){
        super("Order with id: " + orderId + " not found!");
    }
}
