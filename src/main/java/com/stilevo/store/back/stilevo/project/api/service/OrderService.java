package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.OrderItemRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.OrderRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.*;
import com.stilevo.store.back.stilevo.project.api.domain.repository.OrderRepository;
import com.stilevo.store.back.stilevo.project.api.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, UserService userService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
    }

    @Transactional(readOnly = true)
    public List<Order> getAllByUserId(Long userId) {
        return orderRepository.findAllByUser_Id(userId);
    }

    @Transactional(readOnly = true)
    public Order findByID(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order doesn't exist with this Id"));
    }

    @Transactional
    public Order save(OrderRequestDTO orderRequestDTO) {
        User user = userService.findById(orderRequestDTO.getUserId());

        Order order = new Order();

        order.setUser(user); // determina a referencia

        return orderRepository.save(order);
    }

    @Transactional
    public Order delete(Long orderId) {
        Order order = findByID(orderId);

        orderRepository.delete(order);

        return order;
    }

    @Transactional
    public OrderItem putOrderItem(Long orderId, OrderItemRequestDTO orderItemRequestDTO) {
        try {
            Order order = orderRepository.getReferenceById(orderId);

            CartItem cartItem = cartService.findCartItemAtUserCart(orderItemRequestDTO.getUserId(), orderItemRequestDTO.getCartItemId());

            OrderItem orderItem = mapCartItemToOrderItem(cartItem);

            orderItem.setOrder(order);

            order.addItem(orderItem);

            return orderRepository.save(order).getOrderItems().get(order.getOrderItems().size() - 1);
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
