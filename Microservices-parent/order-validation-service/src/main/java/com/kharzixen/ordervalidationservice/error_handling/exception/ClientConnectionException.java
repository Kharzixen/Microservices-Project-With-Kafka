package com.kharzixen.ordervalidationservice.error_handling.exception;

public class ClientConnectionException extends RuntimeException{
    public ClientConnectionException(String msg){
        super(msg);
    }
}
