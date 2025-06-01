package com.stilevo.store.back.stilevo.project.api.controller;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.OrderItemRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.OrderRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.OrderItemResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.OrderResponseDTO;
import com.stilevo.store.back.stilevo.project.api.mapper.OrderMapper;
import com.stilevo.store.back.stilevo.project.api.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "api/orders")
public class OrderController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrdersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getAllByUserId(userId).stream()
                .map(orderMapper::toResponse)
                .toList());
    }

    @PostMapping(value = "/")
    public ResponseEntity<OrderResponseDTO> save(
            @RequestBody @Valid OrderRequestDTO orderRequestDTO
    ) {
        OrderResponseDTO responseDTO = orderMapper.toResponse(orderService.save(orderRequestDTO));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(responseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @DeleteMapping(value = "/{orderId}")
    public ResponseEntity<OrderResponseDTO> delete(
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(orderMapper.toResponse(orderService.delete(orderId)));
    }

    @PostMapping(value = "/{orderId}")
    public ResponseEntity<OrderItemResponseDTO> addOrderItem(
            @PathVariable Long orderId,
            @RequestBody @Valid OrderItemRequestDTO orderItemRequestDTO
    ) {
        return ResponseEntity.ok(orderMapper.toResponse(orderService.putOrderItem(orderId, orderItemRequestDTO)));
    }

}
