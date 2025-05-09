package com.stilevo.store.back.stilevo.project.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId // vai ter a mesma PK da relacao que tem OneToOne
    @JoinColumn(name = "user_id") // deixa no banco o dado com "id", pois o MapsId nao faz isso.
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // se remover da lista, remove do banco tambem
    private List<CartItem> cartItems = new ArrayList<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Cart cart = (Cart) o;
        return getId() != null && Objects.equals(getId(), cart.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public void addProduct(ProductVariation productVariation) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProductVariation().equals(productVariation)) {
                cartItem.addQuantity();
                return;
            }
        }
        CartItem newCartItem = new CartItem();
        newCartItem.setCart(this);
        newCartItem.setProductVariation(productVariation);
        cartItems.add(newCartItem);
    }
}
