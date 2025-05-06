package com.stilevo.store.back.stilevo.project.api.controller;

import com.stilevo.store.back.stilevo.project.api.domain.dto.response.ProductVariationResponseDTO;
import com.stilevo.store.back.stilevo.project.api.mapper.ProductVariationMapper;
import com.stilevo.store.back.stilevo.project.api.service.ProductVariationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/products/variation")
public class ProductVariationController {

    private final ProductVariationMapper mapper;

    private final ProductVariationService productVariationService;

    public ProductVariationController(ProductVariationMapper mapper, ProductVariationService productVariationService) {
        this.mapper = mapper;
        this.productVariationService = productVariationService;
    }

    @GetMapping(value = "GET/all")
    public ResponseEntity<List<ProductVariationResponseDTO>> findAll() {
        return ResponseEntity.ok(productVariationService.findAll().stream()
                .map(mapper::toResponse)
                .toList());
    }

    @GetMapping(value = "GET/{id}")
    public ResponseEntity<ProductVariationResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(productVariationService.findById(id)));
    }
}
