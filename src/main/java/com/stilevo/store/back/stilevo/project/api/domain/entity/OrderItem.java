package com.stilevo.store.back.stilevo.project.api.domain.entity;

import com.stilevo.store.back.stilevo.project.api.domain.enums.Size;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

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

    // precisa vir junto mesmo.
    @ManyToOne
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariation;

    // carregamento Eager, configurar uma consulta personalizada, posteriormente
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private Size size;

    private int quantity;

    private BigDecimal unitPrice;
    // se o dinheiro do produto mudar, ele registra o preco unitario da compra

    private BigDecimal totalPrice;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(productVariation, orderItem.productVariation) && Objects.equals(order, orderItem.order) && size == orderItem.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productVariation, order, size);
    }

    public void addQuantity() {
        this.quantity++;
        this.totalPrice = totalPrice.add(productVariation.getPriceProduct());
    }

    public void decreaseQuantity() {
        this.quantity--;
        this.totalPrice = totalPrice.subtract(productVariation.getPriceProduct());
    }
}
