package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.OrderItemRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.OrderRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.OrderItemResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.OrderResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.*;
import com.stilevo.store.back.stilevo.project.api.domain.repository.OrderRepository;
import com.stilevo.store.back.stilevo.project.api.exception.NotFoundException;
import com.stilevo.store.back.stilevo.project.api.mapper.OrderMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;

    public OrderService(OrderMapper orderMapper, OrderRepository orderRepository, UserService userService, CartService cartService) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getAllByUserId(Long userId) {
        return orderRepository.findAllByUser_Id(userId).stream()
                .map(orderMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO findByID(Long orderId) {
        return orderMapper.toResponse(orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order doesn't exist with this Id")));
    }

    @Transactional
    public OrderResponseDTO save(OrderRequestDTO orderRequestDTO) {
        User user = userService.getReferenceById(orderRequestDTO.getUserId());

        Order order = new Order();

        order.setUser(user); // determina a referencia

        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponseDTO delete(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order doesn't exist with this Id"));

        orderRepository.delete(order);

        return orderMapper.toResponse(order);
    }

    @Transactional
    public OrderItemResponseDTO putOrderItem(Long orderId, OrderItemRequestDTO orderItemRequestDTO) {
        try {
            Order order = orderRepository.getReferenceById(orderId);

            CartItem cartItem = cartService.findCartItemAtUserCart(orderItemRequestDTO.getUserId(), orderItemRequestDTO.getCartItemId());

            OrderItem orderItem = mapCartItemToOrderItem(cartItem);

            orderItem.setOrder(order);

            order.addItem(orderItem);

            return orderMapper.toResponse(orderRepository.save(order).getOrderItems().get(order.getOrderItems().size() - 1));
        } catch (EntityNotFoundException exception) {
            throw new NotFoundException("pedido do usuario de id: " + orderId + " nao existe");
        }
    }

    private OrderItem mapCartItemToOrderItem(CartItem cartItem) {
        OrderItem orderItem = new OrderItem();

        orderItem.setSize(cartItem.getSize());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setTotalPrice(cartItem.getTotalPrice());
        orderItem.setUnitPrice(cartItem.getProductVariation().getPriceProduct());
        orderItem.setProductVariation(cartItem.getProductVariation());

        return orderItem;
    }
}
