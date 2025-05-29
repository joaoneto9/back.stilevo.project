package com.stilevo.store.back.stilevo.project.api.controller;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.ProductVariationRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.ProductVariationResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import com.stilevo.store.back.stilevo.project.api.mapper.ProductVariationMapper;
import com.stilevo.store.back.stilevo.project.api.service.ProductVariationService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @GetMapping(value = "/")
    public ResponseEntity<List<ProductVariationResponseDTO>> findAll() {
        return ResponseEntity.ok(productVariationService.findAll().stream()
                .map(mapper::toResponse)
                .toList());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductVariationResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(productVariationService.findById(id)));
    }

    @PostMapping(value = "/")
    public ResponseEntity<ProductVariationResponseDTO> save(@RequestBody @Valid ProductVariationRequestDTO productVariationRequestDTO) {
        ProductVariationResponseDTO responseDTO = mapper.toResponse(productVariationService.save(productVariationRequestDTO, mapper));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(responseDTO.getId()).toUri();
        // retrona o caminho qeu criou o produto mais o id desse produto, sendo retornado no header

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductVariationResponseDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(productVariationService.delete(id)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductVariationResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid ProductVariationRequestDTO newProduct
    ) {
        return ResponseEntity.ok(mapper.toResponse(productVariationService.update(id, newProduct)));
    }


}
