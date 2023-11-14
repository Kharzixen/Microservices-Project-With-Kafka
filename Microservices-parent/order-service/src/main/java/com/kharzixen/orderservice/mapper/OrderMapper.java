package com.kharzixen.orderservice.mapper;

import com.kharzixen.orderservice.dto.OrderDto;
import com.kharzixen.orderservice.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto modelToDto(Order order);

    List<OrderDto> modelsToDtos(List<Order> carts);
}
