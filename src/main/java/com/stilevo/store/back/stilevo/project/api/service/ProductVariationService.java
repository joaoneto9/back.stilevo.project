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

    private CacheKeyPairContract<String, Page<ProductVariation>> cachePageByName;

    // cache de produtos -> LRU politic
    private CacheKeyPairContract<Long, ProductVariation> cache;

    private boolean isUptodate; // isso vai evitar realizar um cache errado no page

    private final ProductVariationMapper productVariationMapper;
    private final ProductVariationRepository productVariationRepository;
    private final ProductService productService;

    public ProductVariationService(ProductVariationMapper productVariationMapper, ProductVariationRepository productVariationRepository, ProductService productService) {
        this.productVariationMapper = productVariationMapper;
        this.productVariationRepository = productVariationRepository;
        this.productService = productService;

        this.cache = new CacheKeyPairImpl<>();
        this.cachePageByName = new CacheKeyPairImpl<>();
        this.isUptodate = true;
    }

    @Transactional(readOnly = true)
    public Page<ProductVariationResponseDTO> findAll(Pageable pageable, String refPage) {
        Page<ProductVariation> page = cachePageByName.containsKey(refPage) && isUptodate ?
                cachePageByName.getValueOfKey(refPage) : productVariationRepository.findAllVariationsWithProducts(pageable);

        if (cachePageByName.containsKey(refPage))
            cachePageByName.update(refPage, page);
        else
            cachePageByName.add(refPage, page);

        if (!isUptodate)
            isUptodate = true;

        return page.map(productVariationMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ProductVariationResponseDTO> findAllBySimilarName(Pageable pageable, String name) {

        Page<ProductVariation> page = cachePageByName.containsKey(name) && isUptodate ?
                cachePageByName.getValueOfKey(name) : productVariationRepository.findAllBySimilarNameWithProducts(pageable, name);

        if (cachePageByName.containsKey(name))
            cachePageByName.update(name, page);
        else
            cachePageByName.add(name, page);

        if (!isUptodate)
            isUptodate = true;

        return page.map(productVariationMapper::toResponse);
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

        isUptodate = false; // a page pode vai estar desatualizada, ao buscar no cache.

        return productVariationMapper.toResponse(productVariationRepository.save(productVariation)); // salva no banco
    }

    @Transactional
    public ProductVariationResponseDTO delete(Long id) {
        try {
             ProductVariation productVariation = cache.containsKey(id) ?
                     cache.getValueOfKey(id) : productVariationRepository.getReferenceById(id);

            productVariationRepository.delete(productVariation);

            if (cache.containsKey(id))
                cache.remove(id);

            // aqui realmente removeu do banco...
            isUptodate = false;

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

            if (cache.containsKey(id)) // atualiza apenas no produto unico, a paginação do cache continua desatualizada..
                cache.update(id, productVariation);

            ProductVariationResponseDTO responseDTO = productVariationMapper.toResponse(productVariationRepository.save(productVariation));

            isUptodate = false;

            return responseDTO;
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
