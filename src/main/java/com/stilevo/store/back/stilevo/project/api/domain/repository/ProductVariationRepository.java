package com.stilevo.store.back.stilevo.project.api.domain.repository;

import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {

    // busca o produto variado acompanhado do produto ja, por meio do JOIN FETCH, evita N + 1 consultas.
    @Query("SELECT variation FROM ProductVariation variation JOIN FETCH variation.product")
    Page<ProductVariation> findAllVariationsWithProducts(Pageable pageable);
}
