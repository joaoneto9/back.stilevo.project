package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.controller.exception.ConflictException;
import com.stilevo.store.back.stilevo.project.api.controller.exception.NotFoundException;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.ProductRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.ProductResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Product;
import com.stilevo.store.back.stilevo.project.api.domain.repository.ProductRespository;
import com.stilevo.store.back.stilevo.project.api.mapper.ProductMapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRespository productRespository;

    public ProductService(ProductRespository productRespository) {
        this.productRespository = productRespository;
    }

    public List<Product> findAll() {
        return productRespository.findAll();
    }

    public Product findById(Long id) {
        return productRespository.findById(id)
                .orElseThrow(() -> new NotFoundException("erro ao buscar produto com id " + id));
    }

    public ProductResponseDTO save(ProductRequestDTO productRequestDTO, ProductMapper mapper) {
        if (productRespository.findByName(productRequestDTO.getName()).isPresent())
            throw new ConflictException("produto com esse nome ja existe");

        return mapper.toResponse(productRespository.save(mapper.toEntity(productRequestDTO)));
    }
}
