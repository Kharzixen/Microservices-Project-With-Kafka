package com.kharzixen.productservice.error_handling.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String id) {
        super("Product not found with ID: " + id);
    }
}
