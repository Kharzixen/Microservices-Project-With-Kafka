package com.kharzixen.cartservice.mapper;

import com.kharzixen.cartservice.dto.request.ItemDtoIn;
import com.kharzixen.cartservice.dto.response.ItemDtoOut;
import com.kharzixen.cartservice.dto.response.ItemDtoOutDetailed;
import com.kharzixen.cartservice.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    ItemDtoOut modelToDto(Item item);

    ItemDtoOutDetailed modelToDetailedDto(Item item);

    Item modelFromDto(ItemDtoIn itemDtoIn);


    List<ItemDtoOut> modelsToDtos(List<Item> items);

    List<ItemDtoOutDetailed> modelsToDetailedDtos(List<Item> items);
}
