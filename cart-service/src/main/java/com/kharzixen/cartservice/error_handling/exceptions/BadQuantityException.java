package com.kharzixen.cartservice.error_handling.exceptions;

public class BadQuantityException extends RuntimeException{

    public BadQuantityException(String itemId, int quantity){
        super("For item with id: " + itemId + " " + quantity + " quantity is invalid");
    }
}
