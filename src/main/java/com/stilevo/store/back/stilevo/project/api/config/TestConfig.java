package com.stilevo.store.back.stilevo.project.api.config;

import com.stilevo.store.back.stilevo.project.api.domain.entity.Product;
import com.stilevo.store.back.stilevo.project.api.domain.repository.ProductRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class TestConfig implements CommandLineRunner {

    @Autowired
    private ProductRespository productRespository;

    @Override
    public void run(String... args) throws Exception {
        Product p1 = new Product(null, "Camisa Básica", "Camisa de algodão simples", new BigDecimal("49.90"));
        Product p2 = new Product(null, "Calça Jeans", "Calça jeans muito confortavel", new BigDecimal("129.90"));
        Product p3 = new Product(null, "Tênis Esportivo", "Tênis confortável para caminhada", new BigDecimal("199.90"));

        productRespository.saveAll(List.of(p1, p2, p3));
    }
}
