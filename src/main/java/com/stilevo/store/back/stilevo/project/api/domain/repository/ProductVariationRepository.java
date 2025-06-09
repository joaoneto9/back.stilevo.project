package com.stilevo.store.back.stilevo.project.api.domain.repository;

import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {

    // busca o produto variado acompanhado do produto ja.
    @Query("SELECT variation FROM ProductVariation variation JOIN FETCH variation.product")
    List<ProductVariation> findAllVariationsOfProducts();
}
