package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.OrderRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Order;
import com.stilevo.store.back.stilevo.project.api.domain.entity.User;
import com.stilevo.store.back.stilevo.project.api.domain.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public List<Order> getAllByUserId(Long userId) {
        return orderRepository.findALlByUserId(userId);
    }


    @Transactional
    public Order save(OrderRequestDTO orderRequestDTO) {
        User user = userService.findById(orderRequestDTO.getUserId());

        Order order = new Order();

        order.setUser(user); // determina a referencia

        return orderRepository.save(order);
    }

}
