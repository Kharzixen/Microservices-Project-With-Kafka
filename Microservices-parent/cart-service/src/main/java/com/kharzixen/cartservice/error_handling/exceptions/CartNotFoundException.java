package com.kharzixen.cartservice.error_handling.exceptions;

public class CartNotFoundException extends RuntimeException{

        public CartNotFoundException(String identifier, String id) {
            super("Cart not found with " + identifier + " : " + id);
        }

}
