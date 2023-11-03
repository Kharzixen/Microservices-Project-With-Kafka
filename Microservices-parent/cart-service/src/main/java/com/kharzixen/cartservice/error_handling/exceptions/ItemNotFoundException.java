package com.kharzixen.cartservice.error_handling.exceptions;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException(String itemId){
        super("Item with id: " + itemId + " not found");
    }

    public ItemNotFoundException(String cartId, String itemId){
        super("Item with itemId: " + itemId + " not found in cart with cartId: " + cartId);
    }
}
