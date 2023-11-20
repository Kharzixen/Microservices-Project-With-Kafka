package com.kharzixen.ordervalidationserviceretry1.errorHandling.exception;

public class OrderInvalidException extends RuntimeException{
    public OrderInvalidException(String msg){
        super(msg);
    }
}
