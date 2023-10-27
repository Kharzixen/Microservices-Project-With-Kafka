package com.kharzixen.cartservice.error_handling.exceptions;

public class CartAlreadyExistsException extends RuntimeException{
    public CartAlreadyExistsException(String id){
        super("Cart with UserId: " + id + " already exists");
    }
}
