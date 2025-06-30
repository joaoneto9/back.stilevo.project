package com.stilevo.store.back.stilevo.project.api.controller;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.ProductVariationRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.ProductVariationResponseDTO;
import com.stilevo.store.back.stilevo.project.api.service.ProductVariationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "api/products/variation")
public class ProductVariationController {

    private final ProductVariationService productVariationService;
    public ProductVariationController(ProductVariationService productVariationService) {
        this.productVariationService = productVariationService;
    }

    @GetMapping
    public ResponseEntity<List<ProductVariationResponseDTO>> findAll(
            @RequestParam(required = false, defaultValue = "") String name
    ) {
        if (!name.isEmpty())
            return ResponseEntity.ok(productVariationService.findAllBySimilarName(name));

        return ResponseEntity.ok(productVariationService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductVariationResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productVariationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductVariationResponseDTO> save(@RequestBody @Valid ProductVariationRequestDTO productVariationRequestDTO) {
        ProductVariationResponseDTO responseDTO = productVariationService.save(productVariationRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(responseDTO.getId()).toUri();
        // retrona o caminho qeu criou o produto mais o id desse produto, sendo retornado no header

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductVariationResponseDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(productVariationService.delete(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductVariationResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid ProductVariationRequestDTO newProduct
    ) {
        return ResponseEntity.ok(productVariationService.update(id, newProduct));
    }


}
