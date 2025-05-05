package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.controller.exception.NotFoundException;
import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import com.stilevo.store.back.stilevo.project.api.domain.repository.ProductVariationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductVariationService {

    private final ProductVariationRepository productVariationRepository;

    public ProductVariationService(ProductVariationRepository productVariationRepository) {
        this.productVariationRepository = productVariationRepository;
    }

    public List<ProductVariation> findAll() {
        return productVariationRepository.findAll();
    }

    public ProductVariation findById(Long id) {
        return productVariationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product Variation not found at id: " + id));
    }
}
