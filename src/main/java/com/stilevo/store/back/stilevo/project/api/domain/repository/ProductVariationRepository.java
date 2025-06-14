package com.stilevo.store.back.stilevo.project.api.domain.repository;

import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {

    // busca o produto variado acompanhado do produto ja, por meio do JOIN FETCH, evita N + 1 consultas.
    @Query("SELECT variation FROM ProductVariation variation JOIN FETCH variation.product")
    List<ProductVariation> findAllVariationsWithProducts();

    /*
    *CONCAT('%', :nome, '%') serve para montar uma busca "parecida" (semelhante)
    *, verificando se a palavra aparece em qualquer lugar do nome.
    *
    * Se quiser:
    *
    * Começando com o nome: CONCAT(:nome, '%')
    * Terminando com o nome: CONCAT('%', :nome)
    */
    @Query("SELECT variation FROM ProductVariation variation " +
            "JOIN FETCH variation.product WHERE LOWER(variation.product.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<ProductVariation> findAllBySimilarName(String name);
}
