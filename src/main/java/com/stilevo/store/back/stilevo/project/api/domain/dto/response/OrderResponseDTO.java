package com.stilevo.store.back.stilevo.project.api.domain.dto.response;

import com.stilevo.store.back.stilevo.project.api.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    private Long id;
    private List<OrderItemResponseDTO> orderItems;
    private OrderStatus orderStatus;
}
