package com.kharzixen.cartservice.error_handling.exceptions;

public class CartNotFoundException extends RuntimeException{

        public CartNotFoundException(String id) {
            super("Cart not found with ID: " + id);
        }

}
