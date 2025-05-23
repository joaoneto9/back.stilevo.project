package com.stilevo.store.back.stilevo.project.api.domain.entity;
import com.stilevo.store.back.stilevo.project.api.domain.enums.Size;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements Serializable { // tabela intermediaria

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart; // que vai ser o mesmo do Usuario

    @ManyToOne
    @JoinColumn(name = "product_variation_id")
    @EqualsAndHashCode.Include
    private ProductVariation productVariation;

    private BigDecimal totalPrice = BigDecimal.ZERO;

    private int quantity; // comeca com um zero mesmo

    @Enumerated(EnumType.STRING)
    private Size size;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(productVariation, cartItem.productVariation) && size == cartItem.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productVariation, size);
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
