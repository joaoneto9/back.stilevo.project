package com.stilevo.store.back.stilevo.project.api.domain.repository;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
