package com.kharzixen.cartservice.mapper;

import com.kharzixen.cartservice.dto.CartDtoIn;
import com.kharzixen.cartservice.dto.CartDtoOut;
import com.kharzixen.cartservice.dto.ItemDtoIn;
import com.kharzixen.cartservice.dto.ItemDtoOut;
import com.kharzixen.cartservice.model.Cart;
import com.kharzixen.cartservice.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    ItemDtoOut modelToDto(Item item);

    Item modelFromDto(ItemDtoIn itemDtoIn);


    List<ItemDtoOut> modelsToDtos(List<Item> items);
}
