package com.kharzixen.cartservice.error_handling.exceptions;

public class ItemInCartException extends RuntimeException{
    public ItemInCartException(String cartId, String productId, String itemId) {
        super("In Cart with cartId: "
                + cartId + " the product with productId: "
                + productId + " already exists with entryId: "
                + itemId);
    }
}
