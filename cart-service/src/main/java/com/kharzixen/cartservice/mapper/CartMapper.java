package com.kharzixen.cartservice.mapper;

import com.kharzixen.cartservice.dto.CartDtoIn;
import com.kharzixen.cartservice.dto.CartDtoOut;
import com.kharzixen.cartservice.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartDtoOut modelToDto(Cart cart);

    Cart modelFromDto(CartDtoIn cartDtoIn);


    List<CartDtoOut> modelsToDtos(List<Cart> carts);

}
