package com.stilevo.store.back.stilevo.project.api.domain.entity;

import com.stilevo.store.back.stilevo.project.api.domain.enums.Size;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductVariation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Size size;

    private String color;

    private Integer deposit; //quantidades dessa roupa que tem no deposito

    private String imagemUrl;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
