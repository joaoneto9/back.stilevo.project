package com.stilevo.store.back.stilevo.project.api.domain.repository;


import com.stilevo.store.back.stilevo.project.api.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
