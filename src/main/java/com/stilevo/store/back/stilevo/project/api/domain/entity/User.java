package com.stilevo.store.back.stilevo.project.api.domain.entity;

import com.stilevo.store.back.stilevo.project.api.domain.entity.embeddable.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    @Embedded
    private Endereco endereco;

    @OneToOne
    @MapsId
    private Cart cart;
}
