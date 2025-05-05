package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.controller.exception.NotFoundException;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Product;
import com.stilevo.store.back.stilevo.project.api.domain.repository.ProductRespository;
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
}
