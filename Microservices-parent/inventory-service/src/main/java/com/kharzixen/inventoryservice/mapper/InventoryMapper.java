package com.kharzixen.inventoryservice.mapper;

import com.kharzixen.inventoryservice.dto.incomming.InventoryInDto;
import com.kharzixen.inventoryservice.dto.outgoing.InventoryOutDto;
import com.kharzixen.inventoryservice.model.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface InventoryMapper {
    InventoryMapper INSTANCE = Mappers.getMapper(InventoryMapper.class);

    InventoryOutDto modelToDto(Inventory inventory);
    Inventory dtoToModel(InventoryInDto inventoryInDto);

    List<InventoryOutDto> modelsToDtos(List<Inventory> inventoryList);
}
