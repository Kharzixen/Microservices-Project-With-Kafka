package com.kharzixen.ordervalidationserviceretry1.service;

import com.kharzixen.ordervalidationserviceretry1.client.InventoryClient;
import com.kharzixen.ordervalidationserviceretry1.dto.InventoryDto;
import com.kharzixen.ordervalidationserviceretry1.dto.OrderDto;
import com.kharzixen.ordervalidationserviceretry1.dto.OrderItemDto;
import com.kharzixen.ordervalidationserviceretry1.errorHandling.exception.OrderInvalidException;
import com.kharzixen.ordervalidationserviceretry1.model.Order;
import com.kharzixen.ordervalidationserviceretry1.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Log
public class OrderValidationService {

    private final InventoryClient inventoryClient;

    public boolean isValidInventory(Order order) {
        List<String> itemIdList = order.getOrderItemList().stream().map(OrderItem::getProductId).toList();
        Map<String, Integer> inventoryMap = getInventoryMap(inventoryClient.getInventoryByProductId(itemIdList));
        List<String> lowQuantityItems = validateInventory(inventoryMap, order);
        return lowQuantityItems.isEmpty();
    }

    private static Map<String, Integer> getInventoryMap(List<InventoryDto> inventory) {
        Map<String, Integer> inventoryMap = new HashMap<>();
        inventory.forEach(inventoryDto -> {
            inventoryMap.put(inventoryDto.getProductId(), inventoryDto.getQuantity());
        });
        return inventoryMap;
    }

    private static List<String> validateInventory(Map<String, Integer> inventoryMap, Order order) {
        List<String> lowQuantityItems = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItemList()) {
            if (inventoryMap.get(orderItem.getProductId()) == null) {
                log.severe("Unsupported item with id: " + orderItem.getProductId() +
                        ", order is refused (inventory returned null)");
                throw new OrderInvalidException("Unsupported item with id: " + orderItem.getProductId() +
                        ", order is refused (inventory returned null)");
            }
            if (orderItem.getQuantity() > inventoryMap.get(orderItem.getProductId())) {
                lowQuantityItems.add(orderItem.getProductId());
            }
        }
        return lowQuantityItems;
    }

}
