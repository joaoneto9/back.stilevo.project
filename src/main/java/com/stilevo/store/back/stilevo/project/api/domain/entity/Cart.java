package com.stilevo.store.back.stilevo.project.api.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL) // sempre vai ter o Id do cliente correspondente.
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true) // se remover da lista, remove do banco tambem
    private List<CartItem> cartItems = new ArrayList<>();

    public boolean existProduct(ProductVariation productVariation) {

        for (CartItem cartItem : cartItems) {
            if (cartItem.getProductVariation().equals(productVariation))
                return true;
        }
        return false;
    }

}
