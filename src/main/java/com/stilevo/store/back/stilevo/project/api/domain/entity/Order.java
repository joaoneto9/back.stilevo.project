package com.stilevo.store.back.stilevo.project.api.domain.entity;

import com.stilevo.store.back.stilevo.project.api.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> ordersItem = new ArrayList<>();

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
}
