package com.stilevo.store.back.stilevo.project.api.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
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

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true) // se remover da lista, remove do banco tambem
    private List<CartItem> cartItems = new ArrayList<>();


}
