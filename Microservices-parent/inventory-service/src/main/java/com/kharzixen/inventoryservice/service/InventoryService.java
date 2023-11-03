package com.kharzixen.inventoryservice.service;

import com.kharzixen.inventoryservice.dto.incomming.InventoryInDto;
import com.kharzixen.inventoryservice.dto.outgoing.InventoryOutDto;
import com.kharzixen.inventoryservice.mapper.InventoryMapper;
import com.kharzixen.inventoryservice.model.Inventory;
import com.kharzixen.inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryOutDto createInventory(InventoryInDto inventoryInDto){
        Inventory inventory = InventoryMapper.INSTANCE.dtoToModel(inventoryInDto);
        if(inventory.getSellingPrice() == null){
            inventory.setSellingPrice(BigDecimal.valueOf(0.0));
        }
        if(inventory.getQuantity() == null){
            inventory.setQuantity(0);
        }
        Inventory saved = inventoryRepository.save(inventory);
        return InventoryMapper.INSTANCE.modelToDto(saved);
    }

    public List<InventoryOutDto> getFullInventory(){
        List<Inventory> inventoryList = inventoryRepository.findAll();
        return InventoryMapper.INSTANCE.modelsToDtos(inventoryList);
    }

    public Optional<InventoryOutDto> getInventoryByProductId(String productId){
        Optional<Inventory> optionalInventory = inventoryRepository.findByProductId(productId);
        return optionalInventory.map(InventoryMapper.INSTANCE::modelToDto);
    }

    public Optional<InventoryOutDto> getInventoryBySkuCode(String skuCode){
        Optional<Inventory> optionalInventory = inventoryRepository.findByStorageKeepingUnit(skuCode);
        return optionalInventory.map(InventoryMapper.INSTANCE::modelToDto);
    }

    public Optional<InventoryOutDto> patchInventoryBySkuCode(String skuCode, InventoryInDto inventoryInDto){
        Inventory newInventory = InventoryMapper.INSTANCE.dtoToModel(inventoryInDto);
        Optional<Inventory> optionalInventory = inventoryRepository.findByStorageKeepingUnit(skuCode);
        Inventory inventory;

        //get the inventory by skuCode, if is not present empty -> not found
        if(optionalInventory.isPresent()){
            inventory = optionalInventory.get();
        } else {
            return Optional.empty();
        }

        //if there is a quantity field in the input object update it and return the updated
        if(newInventory.getQuantity() != null){
            inventory.setQuantity(newInventory.getQuantity());
        }

        Inventory saved = inventoryRepository.save(inventory);
        return Optional.of(InventoryMapper.INSTANCE.modelToDto(saved));
    }

    public Optional<InventoryOutDto> patchInventoryByProductId(String productId, InventoryInDto inventoryInDto){
        Inventory newInventory = InventoryMapper.INSTANCE.dtoToModel(inventoryInDto);
        Optional<Inventory> optionalInventory = inventoryRepository.findByProductId(productId);
        Inventory inventory;

        //get the inventory by skuCode, if is not present empty -> not found
        if(optionalInventory.isPresent()){
            inventory = optionalInventory.get();
        } else {
            return Optional.empty();
        }

        //if there is a quantity field in the input object update it and return the updated
        if(newInventory.getQuantity() != null){
            inventory.setQuantity(newInventory.getQuantity());
        }

        Inventory saved = inventoryRepository.save(inventory);
        return Optional.of(InventoryMapper.INSTANCE.modelToDto(saved));
    }

}
