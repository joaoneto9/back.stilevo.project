package com.stilevo.store.back.stilevo.project.api.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stilevo.store.back.stilevo.project.api.domain.enums.Size;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductVariation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    private Size size;

    private String color;

    private Integer deposit; //quantidades dessa roupa que tem no deposito

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
