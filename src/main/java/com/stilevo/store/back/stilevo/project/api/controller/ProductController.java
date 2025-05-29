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

    private final ProductMapper productMapper;

    private final ProductService productService;

    public ProductController(ProductMapper productMapper, ProductService productService) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        return ResponseEntity.ok(productService.findAll()
                .stream()
                .map(productMapper::toResponse).toList());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> findById( @PathVariable Long id) {
        return ResponseEntity.ok(productMapper.toResponse(productService.findById(id)));
    }

    @PostMapping(value = "/")
    public ResponseEntity<ProductResponseDTO> save(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        ProductResponseDTO responseDTO = productMapper.toResponse(productService.save(productMapper.toEntity(productRequestDTO)));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(responseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(productMapper.toResponse(productService.delete(id)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO productRequestDTO
    ){
        return ResponseEntity.ok(productMapper.toResponse(productService.update(id, productRequestDTO)));
    }

}
