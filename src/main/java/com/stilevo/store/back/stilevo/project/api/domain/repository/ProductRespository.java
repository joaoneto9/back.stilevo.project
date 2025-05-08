package com.stilevo.store.back.stilevo.project.api.domain.repository;

import com.stilevo.store.back.stilevo.project.api.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRespository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
