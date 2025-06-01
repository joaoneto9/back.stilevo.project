package com.stilevo.store.back.stilevo.project.api.mapper;

import com.stilevo.store.back.stilevo.project.api.domain.dto.response.OrderItemResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.OrderResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Order;
import com.stilevo.store.back.stilevo.project.api.domain.entity.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductVariationMapper.class})
public interface OrderMapper{
    OrderResponseDTO toResponse(Order order);
    OrderItemResponseDTO toResponse(OrderItem orderItem);
}
