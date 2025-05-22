package com.stilevo.store.back.stilevo.project.api.domain.entity;

import com.stilevo.store.back.stilevo.project.api.domain.enums.Size;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariation;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private Size size;

    private int quantity;

    private BigDecimal unitPrice;
    // se o dinheiro do produto mudar, ele registra o preco unitario da compra

    private BigDecimal totalPrice;
}
