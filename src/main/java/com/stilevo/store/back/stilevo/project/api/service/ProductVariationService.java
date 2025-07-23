package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.domain.dto.response.ProductVariationResponseDTO;
import com.stilevo.store.back.stilevo.project.api.exception.NotFoundException;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.ProductVariationRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Product;
import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import com.stilevo.store.back.stilevo.project.api.domain.repository.ProductVariationRepository;
import com.stilevo.store.back.stilevo.project.api.mapper.ProductVariationMapper;
import com.stilevo.store.back.stilevo.project.api.service.cache.contract.CacheKeyPairContract;
import com.stilevo.store.back.stilevo.project.api.service.cache.structure.CacheKeyPairImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


@Service
public class ProductVariationService {

    // tentativa de cache
    private CacheKeyPairContract<Long, ProductVariation> cache;

    private final ProductVariationMapper productVariationMapper;
    private final ProductVariationRepository productVariationRepository;
    private final ProductService productService;

    public ProductVariationService(ProductVariationMapper productVariationMapper, ProductVariationRepository productVariationRepository, ProductService productService) {
        this.productVariationMapper = productVariationMapper;
        this.productVariationRepository = productVariationRepository;
        this.productService = productService;
        this.cache = new CacheKeyPairImpl<>();
    }

    @Transactional(readOnly = true)
    public Page<ProductVariationResponseDTO> findAll(Pageable pageable) {
        return productVariationRepository.findAllVariationsWithProducts(pageable)
                .map(productVariationMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ProductVariationResponseDTO findById(Long id) {
        if (cache.containsKey(id))
            return productVariationMapper.toResponse(cache.getValueOfKey(id));

        ProductVariation productVariation = productVariationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product Variation not found at id: " + id));

        cache.add(id, productVariation);

        return productVariationMapper.toResponse(productVariation);
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
            if (cache.containsKey(id)) {
                ProductVariation productVariation = cache.getValueOfKey(id);

                productVariationRepository.delete(productVariation);
                cache.remove(id);

                return productVariationMapper.toResponse(productVariation);
            }

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
            //isso ficou lindo, sou foda.
            ProductVariation productVariation = cache.containsKey(id) ? cache.getValueOfKey(id) : productVariationRepository.getReferenceById(id);

            productVariation.setColor(newProduct.getColor());
            productVariation.setDeposit(newProduct.getDeposit());
            productVariation.setImageUrl(newProduct.getImageUrl());

            if (cache.containsKey(id))
                cache.update(id, productVariation);

            return productVariationMapper.toResponse(productVariationRepository.save(productVariation));
        } catch (EntityNotFoundException exception) {
            throw new NotFoundException("Produto Variado nao Encontrado com id: " + id);
        }

    }

    @Transactional(readOnly = true)
    protected ProductVariation getEntityById( Long id) {
        try {

            if (cache.containsKey(id))
                return cache.getValueOfKey(id);

            return productVariationRepository.getReferenceById(id);
        } catch (EntityNotFoundException exception) {
            throw new NotFoundException("Produto Variado nao Encontrado com id: " + id);
        }
    }
}
