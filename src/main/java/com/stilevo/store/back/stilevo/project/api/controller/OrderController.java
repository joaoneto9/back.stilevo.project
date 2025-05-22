package com.stilevo.store.back.stilevo.project.api.controller;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.OrderRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.OrderResponseDTO;
import com.stilevo.store.back.stilevo.project.api.mapper.OrderMapper;
import com.stilevo.store.back.stilevo.project.api.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "api/order")
public class OrderController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping(value = "/GET/all/{userId}")
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
}
