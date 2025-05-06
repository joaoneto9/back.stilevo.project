package com.stilevo.store.back.stilevo.project.api.config;

import com.stilevo.store.back.stilevo.project.api.domain.entity.Product;
import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import com.stilevo.store.back.stilevo.project.api.domain.enums.Size;
import com.stilevo.store.back.stilevo.project.api.domain.repository.ProductRespository;
import com.stilevo.store.back.stilevo.project.api.domain.repository.ProductVariationRepository;
import com.stilevo.store.back.stilevo.project.api.mapper.ProductVariationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class TestConfig implements CommandLineRunner {

    @Autowired
    private ProductRespository productRespository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    @Override
    public void run(String... args) throws Exception {
        Product p1 = new Product(null, "Camisa Básica", "Camisa de algodão simples", new BigDecimal("49.90"));
        Product p2 = new Product(null, "Calça Jeans", "Calça jeans muito confortavel", new BigDecimal("129.90"));
        Product p3 = new Product(null, "Tênis Esportivo", "Tênis confortável para caminhada", new BigDecimal("199.90"));

        productRespository.saveAll(List.of(p1, p2, p3));

        ProductVariation variation1 = new ProductVariation(null, Size.M, "Azul", 10, "https://imagem.com/camisa-azul.jpg", p1);
        ProductVariation variation2 = new ProductVariation(null, Size.G, "Preto", 5, "https://imagem.com/camisa-preta.jpg", p1);
        ProductVariation variation3 = new ProductVariation(null, Size.P, "Branco", 8, "https://imagem.com/camisa-branca.jpg", p1);

        productVariationRepository.saveAll(List.of(variation1, variation2, variation3));
    }
}
