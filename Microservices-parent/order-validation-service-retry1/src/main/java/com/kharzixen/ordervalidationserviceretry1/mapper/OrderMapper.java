package com.kharzixen.ordervalidationserviceretry1.mapper;

import com.kharzixen.ordervalidationserviceretry1.dto.OrderDto;
import com.kharzixen.ordervalidationserviceretry1.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target="orderItemList", source = "orderItemList")
    Order modelFromDto(OrderDto orderDto);
}
