package com.kharzixen.inventoryservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kharzixen.inventoryservice.dto.incomming.InventoryInDto;
import com.kharzixen.inventoryservice.dto.outgoing.InventoryOutDto;
import com.kharzixen.inventoryservice.event.InventoryChangedEvent;
import com.kharzixen.inventoryservice.mapper.InventoryMapper;
import com.kharzixen.inventoryservice.model.Inventory;
import com.kharzixen.inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public InventoryOutDto createInventory(InventoryInDto inventoryInDto) {
        Inventory inventory = InventoryMapper.INSTANCE.dtoToModel(inventoryInDto);
        if (inventory.getSellingPrice() == null) {
            inventory.setSellingPrice(BigDecimal.valueOf(0.0));
        }
        if (inventory.getQuantity() == null) {
            inventory.setQuantity(0);
        }
        Inventory saved = inventoryRepository.save(inventory);
        InventoryChangedEvent inventoryChangedEvent = new InventoryChangedEvent(saved.getProductId(), saved.getQuantity());
        try {
            kafkaTemplate.send("inventoryChangedTopic", objectMapper.writeValueAsString(inventoryChangedEvent) );
        } catch (JsonProcessingException e) {
            log.error("Could not convert event to json: " + inventoryChangedEvent);
        }
        return InventoryMapper.INSTANCE.modelToDto(saved);
    }

    public List<InventoryOutDto> getFullInventory() {
        List<Inventory> inventoryList = inventoryRepository.findAll();
        return InventoryMapper.INSTANCE.modelsToDtos(inventoryList);
    }

    public Optional<InventoryOutDto> getInventoryByProductId(String productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        Optional<Inventory> optionalInventory = Optional.ofNullable(inventory);
        return optionalInventory.map(InventoryMapper.INSTANCE::modelToDto);
    }

    public Optional<InventoryOutDto> getInventoryBySkuCode(String skuCode) {
        Inventory inventory = inventoryRepository.findByStorageKeepingUnit(skuCode);
        Optional<Inventory> optionalInventory = Optional.ofNullable(inventory);
        return optionalInventory.map(InventoryMapper.INSTANCE::modelToDto);
    }

    public Optional<InventoryOutDto> patchInventoryBySkuCode(String skuCode, InventoryInDto inventoryInDto) {
        Inventory newInventory = InventoryMapper.INSTANCE.dtoToModel(inventoryInDto);
        Optional<Inventory> optionalInventory = Optional.ofNullable(inventoryRepository.findByStorageKeepingUnit(skuCode));
        Inventory inventory;

        //get the inventory by skuCode, if is not present empty -> not found
        if (optionalInventory.isPresent()) {
            inventory = optionalInventory.get();
        } else {
            return Optional.empty();
        }

        //if there is a quantity field in the input object update it and return the updated
        if (newInventory.getQuantity() != null) {
            inventory.setQuantity(newInventory.getQuantity());
        }

        Inventory saved = inventoryRepository.save(inventory);
        return Optional.ofNullable(InventoryMapper.INSTANCE.modelToDto(saved));
    }

    public Optional<InventoryOutDto> patchInventoryByProductId(String productId, InventoryInDto inventoryInDto) {
        Inventory newInventory = InventoryMapper.INSTANCE.dtoToModel(inventoryInDto);
        Optional<Inventory> optionalInventory = Optional.ofNullable(inventoryRepository.findByProductId(productId));
        Inventory inventory;

        //get the inventory by skuCode, if is not present empty -> not found
        if (optionalInventory.isPresent()) {
            inventory = optionalInventory.get();
        } else {
            return Optional.empty();
        }

        //if there is a quantity field in the input object update it and return the updated
        if (newInventory.getQuantity() != null) {
            inventory.setQuantity(newInventory.getQuantity());
        }

        Inventory saved = inventoryRepository.save(inventory);
        return Optional.ofNullable(InventoryMapper.INSTANCE.modelToDto(saved));
    }

    public Optional<List<InventoryOutDto>> getInventoryListByProductIdList(List<String> productIds) {

        if(productIds == null){
            List<Inventory> inventoryList = inventoryRepository.findAll();
            return Optional.ofNullable(InventoryMapper.INSTANCE.modelsToDtos(inventoryList));
        }

        List<InventoryOutDto> inventoryOutDtoList = new ArrayList<>();
        for (String id : productIds) {
            Inventory inventory = inventoryRepository.findByProductId(id);
            if(inventory == null){
                continue;
            }
            inventoryOutDtoList.add(InventoryMapper.INSTANCE.modelToDto(inventory));
        }
        return Optional.of(inventoryOutDtoList);
    }
}
