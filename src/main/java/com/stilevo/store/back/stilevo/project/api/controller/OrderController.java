package com.stilevo.store.back.stilevo.project.api.controller;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.OrderItemRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.OrderRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.OrderResponseDTO;
import com.stilevo.store.back.stilevo.project.api.mapper.OrderMapper;
import com.stilevo.store.back.stilevo.project.api.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/order")
public class OrderController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping(value = "/GET/all/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrdersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getAllByUserId(userId).stream()
                .map(orderMapper::toResponse)
                .toList());
    }

    @PostMapping(value = "/POST/save")
    public ResponseEntity<OrderResponseDTO> save(
            @RequestBody @Valid OrderRequestDTO orderRequestDTO
    ) {
        return ResponseEntity.ok(orderMapper.toResponse(orderService.save(orderRequestDTO)));
    }

    @DeleteMapping(value = "/DELETE/{orderId}")
    public ResponseEntity<OrderResponseDTO> delete(
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(orderMapper.toResponse(orderService.delete(orderId)));
    }

    @PutMapping(value = "/PUT/orderItem/{orderId}")
    public ResponseEntity<OrderResponseDTO> putOrderItem(
            @PathVariable Long orderId,
            @RequestBody @Valid OrderItemRequestDTO orderItemRequestDTO
    ) {
        return ResponseEntity.ok(orderMapper.toResponse(orderService.putOrderItem(orderId, orderItemRequestDTO)));
    }

}
