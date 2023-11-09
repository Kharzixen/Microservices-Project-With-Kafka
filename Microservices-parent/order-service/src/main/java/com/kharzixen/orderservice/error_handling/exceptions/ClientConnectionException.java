package com.kharzixen.orderservice.error_handling.exceptions;

public class ClientConnectionException extends RuntimeException{
    public ClientConnectionException(String msg){
        super(msg);
    }
}
