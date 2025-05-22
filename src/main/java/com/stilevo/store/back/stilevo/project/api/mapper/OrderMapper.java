package com.stilevo.store.back.stilevo.project.api.mapper;

import com.stilevo.store.back.stilevo.project.api.domain.dto.response.OrderResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper{
    OrderResponseDTO toResponse(Order order);
}
