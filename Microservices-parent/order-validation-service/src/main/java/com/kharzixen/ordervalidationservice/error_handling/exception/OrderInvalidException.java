package com.kharzixen.ordervalidationservice.error_handling.exception;

public class OrderInvalidException extends RuntimeException{
    public OrderInvalidException(String msg){
        super(msg);
    }
}
