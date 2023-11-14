package com.kharzixen.orderservice.mapper;

import com.kharzixen.orderservice.dto.OrderItemDto;
import com.kharzixen.orderservice.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    OrderItemDto modelToDto(OrderItem item);

    List<OrderItemDto> modelsToDtos(List<OrderItem> items);

}