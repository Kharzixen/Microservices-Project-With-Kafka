package com.kharzixen.productservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kharzixen.productservice.event.InventoryChangedEvent;
import com.kharzixen.productservice.model.Product;
import com.kharzixen.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class InventoryChangesListener {
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "inventoryChangedTopic")
    public void handleInventoryChanged(String message){
        try {
            log.info(message);
            InventoryChangedEvent inventoryChangedEvent = objectMapper.readValue(message, InventoryChangedEvent.class);
            Optional<Product> optional = productService.updateProductInventory(inventoryChangedEvent.getProductId(), inventoryChangedEvent.getInventory());
            if(optional.isEmpty()){
                throw new RuntimeException("Cannot update product with id: " + inventoryChangedEvent.getProductId());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
