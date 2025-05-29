package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.exception.NotFoundException;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.ProductVariationRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Product;
import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import com.stilevo.store.back.stilevo.project.api.domain.repository.ProductVariationRepository;
import com.stilevo.store.back.stilevo.project.api.mapper.ProductVariationMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductVariationService {

    private final ProductVariationRepository productVariationRepository;

    private final ProductService productService;

    public ProductVariationService(ProductVariationRepository productVariationRepository, ProductService productService) {
        this.productVariationRepository = productVariationRepository;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public List<ProductVariation> findAll() {
        return productVariationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ProductVariation findById(Long id) {
        return productVariationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product Variation not found at id: " + id));
    }

    @Transactional
    public ProductVariation save(ProductVariationRequestDTO productVariationRequestDTO, ProductVariationMapper mapper) {
        Product product = productService.findById(productVariationRequestDTO.getProductId()); // ja lanca essa excessao no service do Product Service

        ProductVariation productVariation = mapper.toEntity(productVariationRequestDTO); // transforma em entidade

        productVariation.setProduct(product); // set do produto

        return productVariationRepository.save(productVariation); // salva no banco
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
        try {
            ProductVariation productVariation = productVariationRepository.getReferenceById(id);

            productVariation.setColor(newProduct.getColor());
            productVariation.setDeposit(newProduct.getDeposit());
            productVariation.setImageUrl(newProduct.getImageUrl());
            // nao vou mudar o produto, porque nao faz sentido

            return productVariationRepository.save(productVariation);
        } catch (EntityNotFoundException exception) {
            throw new NotFoundException("Produto Variado nao Encontrado com id: " + id);
        }

    }

}
