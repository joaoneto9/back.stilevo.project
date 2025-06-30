package com.stilevo.store.back.stilevo.project.api.controller;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.ProductRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.ProductResponseDTO;
import com.stilevo.store.back.stilevo.project.api.mapper.ProductMapper;
import com.stilevo.store.back.stilevo.project.api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> findById( @PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> save(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        ProductResponseDTO responseDTO = productService.save(productRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(responseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(productService.delete(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO productRequestDTO
    ){
        return ResponseEntity.ok(productService.update(id, productRequestDTO));
    }

}
