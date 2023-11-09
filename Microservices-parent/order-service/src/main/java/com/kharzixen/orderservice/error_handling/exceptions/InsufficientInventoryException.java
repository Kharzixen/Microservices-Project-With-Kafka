package com.kharzixen.orderservice.error_handling.exceptions;

import java.util.Arrays;
import java.util.List;

public class InsufficientInventoryException extends RuntimeException {
    public InsufficientInventoryException(List<String> product){
        super("Insufficient quantity of these products to place order: " + Arrays.toString(product.toArray()));
    }
}
