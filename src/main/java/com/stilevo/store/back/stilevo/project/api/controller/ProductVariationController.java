package com.stilevo.store.back.stilevo.project.api.controller;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.ProductVariationRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.ProductVariationResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import com.stilevo.store.back.stilevo.project.api.mapper.ProductVariationMapper;
import com.stilevo.store.back.stilevo.project.api.service.ProductVariationService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "POST/save")
    public ResponseEntity<ProductVariationResponseDTO> save(@RequestBody @Valid ProductVariationRequestDTO productVariationRequestDTO) {
        return ResponseEntity.ok(mapper.toResponse(productVariationService.save(productVariationRequestDTO, mapper)));
    }

    @DeleteMapping(value = "DELETE/{id}")
    public ResponseEntity<ProductVariationResponseDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(productVariationService.delete(id)));
    }

    @PutMapping(value = "UPDATE/{id}")
    public ResponseEntity<ProductVariationResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid ProductVariationRequestDTO newProduct
    ) {
        return ResponseEntity.ok(mapper.toResponse(productVariationService.update(id, newProduct)));
    }


}
