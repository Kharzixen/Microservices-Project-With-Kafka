package com.kharzixen.orderservice.error_handling.exceptions;

public class OrderBadRequestException extends RuntimeException{
    public OrderBadRequestException(String msg){
        super(msg);
    }
}
