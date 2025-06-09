package com.stilevo.store.back.stilevo.project.api.domain.entity;

import com.stilevo.store.back.stilevo.project.api.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    // realizar um consulta personalizada porsteriormente, carregamento Eager não utilizado.
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // carregamneto Lazy
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING_PAYMENT;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(Id, order.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Id);
    }

    public void addItem(OrderItem orderItem) {
        for (OrderItem item : orderItems) {
            if (item.equals(orderItem)) {
                item.addQuantity();
                return;
            }
        }

        orderItems.add(orderItem);
    }
}
