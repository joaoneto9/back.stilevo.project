package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.domain.dto.response.ProductVariationResponseDTO;
import com.stilevo.store.back.stilevo.project.api.exception.NotFoundException;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.ProductVariationRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Product;
import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import com.stilevo.store.back.stilevo.project.api.domain.repository.ProductVariationRepository;
import com.stilevo.store.back.stilevo.project.api.mapper.ProductVariationMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductVariationService {

    private final ProductVariationMapper productVariationMapper;
    private final ProductVariationRepository productVariationRepository;
    private final ProductService productService;

    public ProductVariationService(ProductVariationMapper productVariationMapper, ProductVariationRepository productVariationRepository, ProductService productService) {
        this.productVariationMapper = productVariationMapper;
        this.productVariationRepository = productVariationRepository;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public List<ProductVariationResponseDTO> findAll() {
        return productVariationRepository.findAll().stream()
                .map(productVariationMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductVariationResponseDTO> findAllBySimilarName(String name) {
        return productVariationRepository.findAllBySimilarName(name).stream()
                .map(productVariationMapper::toResponse)
                .toList();

    }

    @Transactional(readOnly = true)
    public ProductVariationResponseDTO findById(Long id) {
        return productVariationMapper.toResponse(productVariationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product Variation not found at id: " + id)));
    }

    @Transactional
    public ProductVariationResponseDTO save(ProductVariationRequestDTO productVariationRequestDTO) {
        Product product = productService.getEntityById(productVariationRequestDTO.getProductId()); // ja lanca essa excessao no service do Product Service

        ProductVariation productVariation = productVariationMapper.toEntity(productVariationRequestDTO); // transforma em entidade

        productVariation.setProduct(product); // set do produto

        return productVariationMapper.toResponse(productVariationRepository.save(productVariation)); // salva no banco
    }

    @Transactional
    public ProductVariationResponseDTO delete(Long id) {
        try {
            ProductVariation productVariation = productVariationRepository.getReferenceById(id);

            productVariationRepository.delete(productVariation);

            return productVariationMapper.toResponse(productVariation);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Produto Variado nao encontrado com id: " + id);
        }
    }

    @Transactional
    public ProductVariationResponseDTO update(Long id, ProductVariationRequestDTO newProduct) {
        try {
            ProductVariation productVariation = productVariationRepository.getReferenceById(id);

            productVariation.setColor(newProduct.getColor());
            productVariation.setDeposit(newProduct.getDeposit());
            productVariation.setImageUrl(newProduct.getImageUrl());
            // nao vou mudar o produto, porque nao faz sentido

            return productVariationMapper.toResponse(productVariationRepository.save(productVariation));
        } catch (EntityNotFoundException exception) {
            throw new NotFoundException("Produto Variado nao Encontrado com id: " + id);
        }

    }

    @Transactional(readOnly = true)
    protected ProductVariation getEntityById( Long id) {
        try {
            return productVariationRepository.getReferenceById(id);
        } catch (EntityNotFoundException exception) {
            throw new NotFoundException("Produto Variado nao Encontrado com id: " + id);
        }
    }
}
