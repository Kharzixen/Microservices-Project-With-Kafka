package com.kharzixen.cartservice.mapper;

import com.kharzixen.cartservice.dto.request.CartDtoIn;
import com.kharzixen.cartservice.dto.response.CartDtoOut;
import com.kharzixen.cartservice.dto.response.CartDtoOutNoItems;
import com.kharzixen.cartservice.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartDtoOut modelToDto(Cart cart);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "itemList", ignore = true)
    Cart modelFromDto(CartDtoIn cartDtoIn);

    CartDtoOutNoItems modelToDtoNoItems(Cart cart);

    List<CartDtoOut> modelsToDtos(List<Cart> carts);

}
