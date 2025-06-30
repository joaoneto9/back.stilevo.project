package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.ProductRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.ProductResponseDTO;
import com.stilevo.store.back.stilevo.project.api.exception.ConflictException;
import com.stilevo.store.back.stilevo.project.api.exception.NotFoundException;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Product;
import com.stilevo.store.back.stilevo.project.api.domain.repository.ProductRespository;
import com.stilevo.store.back.stilevo.project.api.mapper.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRespository productRespository;

    public ProductService(ProductMapper productMapper, ProductRespository productRespository) {
        this.productMapper = productMapper;
        this.productRespository = productRespository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll() {
        return productRespository.findAll().stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        return productMapper.toResponse(productRespository.findById(id)
                .orElseThrow(() -> new NotFoundException("erro ao buscar produto com id " + id)));
    }

    @Transactional(readOnly = true)
    protected Product getEntityById(Long id) {
        return productRespository.findById(id)
                .orElseThrow(() -> new NotFoundException("erro ao buscar produto com id " + id));
    }

    @Transactional
    public ProductResponseDTO save(ProductRequestDTO product) {
        if (productRespository.findByName(product.getName()).isPresent())
            throw new ConflictException("produto com esse nome ja existe");

        return productMapper.toResponse(productRespository.save(productMapper.toEntity(product)));
    }

    @Transactional
    public ProductResponseDTO delete(Long id) {
        try {
            Product product = productRespository.getReferenceById(id); // encontra

            productRespository.delete(product); // deleta

            return productMapper.toResponse(product); // retorna
        } catch (EntityNotFoundException exception) {
            throw new NotFoundException("erro ao buscar produto com id " + id);
        }
    }

    @Transactional
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        try {
            Product product = productRespository.getReferenceById(id);

            product.setDescription(productRequestDTO.getDescription()); // seta a descriscao
            product.setName(productRequestDTO.getName()); // seta o nome
            product.setPrice(productRequestDTO.getPrice()); // seta o preco

            return productMapper.toResponse(productRespository.save(product)); // atualiza
        } catch (EntityNotFoundException exception) {
            throw new NotFoundException("erro ao buscar produto com id: " + id);
        }
    }


}
