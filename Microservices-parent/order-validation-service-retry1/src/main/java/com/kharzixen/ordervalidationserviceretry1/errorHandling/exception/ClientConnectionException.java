package com.kharzixen.ordervalidationserviceretry1.errorHandling.exception;

public class ClientConnectionException extends RuntimeException{
    public ClientConnectionException(String msg){
        super(msg);
    }
}
