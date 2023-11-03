package com.kharzixen.inventoryservice.error_handling.exception;


public class InventoryNotFoundException extends RuntimeException{
    public InventoryNotFoundException(){
        super("Inventory not found! No skuCode or productId provided");
    }
    public InventoryNotFoundException(String idType, String id){
        super("Inventory not found with " + idType + ": " + id);
    }
}
