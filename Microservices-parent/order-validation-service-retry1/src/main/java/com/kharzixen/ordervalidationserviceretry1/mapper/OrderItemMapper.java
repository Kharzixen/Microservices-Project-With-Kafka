package com.kharzixen.ordervalidationserviceretry1.mapper;

import com.kharzixen.ordervalidationserviceretry1.dto.OrderItemDto;
import com.kharzixen.ordervalidationserviceretry1.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);
    OrderItem modelFromDto(OrderItemDto orderItemDto);
    List<OrderItem> modelsFromDtos(List<OrderItemDto> orderItemList);
}
