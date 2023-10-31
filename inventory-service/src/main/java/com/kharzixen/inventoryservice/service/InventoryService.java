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

    public Optional<InventoryOutDto> updateInventory(InventoryInDto inventoryInDto){
        return Optional.empty();
    }
}
