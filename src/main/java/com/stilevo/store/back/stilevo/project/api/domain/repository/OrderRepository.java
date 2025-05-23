package com.stilevo.store.back.stilevo.project.api.domain.repository;

import com.stilevo.store.back.stilevo.project.api.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser_Id(Long id);
}
