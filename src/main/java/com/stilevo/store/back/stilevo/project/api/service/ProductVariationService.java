package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.controller.exception.NotFoundException;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.ProductVariationRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.ProductVariationResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Product;
import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import com.stilevo.store.back.stilevo.project.api.domain.repository.ProductRespository;
import com.stilevo.store.back.stilevo.project.api.domain.repository.ProductVariationRepository;
import com.stilevo.store.back.stilevo.project.api.mapper.ProductVariationMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductVariationService {

    private final ProductVariationRepository productVariationRepository;

    private final ProductRespository productRespository;

    public ProductVariationService(ProductVariationRepository productVariationRepository, ProductRespository productRespository) {
        this.productVariationRepository = productVariationRepository;
        this.productRespository = productRespository;
    }

    public List<ProductVariation> findAll() {
        return productVariationRepository.findAll();
    }

    public ProductVariation findById(Long id) {
        return productVariationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product Variation not found at id: " + id));
    }

    @Transactional
    public ProductVariationResponseDTO save(ProductVariationRequestDTO productVariationRequestDTO, ProductVariationMapper mapper) {
        Optional<Product> product = productRespository.findById(productVariationRequestDTO.getProductId());

        if (product.isEmpty())
            throw new NotFoundException("produto nao existe, passe um id valido"); // se passa o id do produto errado, lanca excessao

        ProductVariation productVariation = mapper.toEntity(productVariationRequestDTO); // transforma em entidade

        productVariation.setProduct(product.get()); //set do produto

        return mapper.toResponse(productVariationRepository.save(productVariation)); // salva no banco
    }

    @Transactional
    public ProductVariation delete(Long id) {
        ProductVariation productVariation = productVariationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto Variado nao encontrado com id: " + id));

        productVariationRepository.delete(productVariation);

        return productVariation;


    }

    @Transactional
    public ProductVariation update(Long id, ProductVariationRequestDTO newProduct) {
        ProductVariation productVariation = productVariationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto Variado nao Encontrado com id: " + id));

        productVariation.setSize(newProduct.getSize());
        productVariation.setColor(newProduct.getColor());
        productVariation.setDeposit(newProduct.getDeposit());
        productVariation.setImageUrl(newProduct.getImageUrl());
        // nao vou mudar o produto, porque nao faz sentido

        return productVariationRepository.save(productVariation);
    }
}
