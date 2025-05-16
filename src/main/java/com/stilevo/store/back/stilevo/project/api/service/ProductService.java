package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.ProductRequestDTO;
import com.stilevo.store.back.stilevo.project.api.exception.ConflictException;
import com.stilevo.store.back.stilevo.project.api.exception.NotFoundException;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Product;
import com.stilevo.store.back.stilevo.project.api.domain.repository.ProductRespository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Product save(Product product) {
        if (productRespository.findByName(product.getName()).isPresent())
            throw new ConflictException("produto com esse nome ja existe");

        return productRespository.save(product);
    }

    @Transactional
    public Product delete(Long id) {
        Product product = findById(id); // encontra

        productRespository.delete(product); // deleta

        return product; // retorna
    }

    @Transactional
    public Product update(Long id, ProductRequestDTO productRequestDTO) {
        Product product = findById(id);

        product.setDescription(productRequestDTO.getDescription()); // seta a descriscao
        product.setName(productRequestDTO.getName()); // seta o nome
        product.setPrice(productRequestDTO.getPrice()); // seta o preco

        return productRespository.save(product); // atualiza
    }
}
