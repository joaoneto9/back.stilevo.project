package com.stilevo.store.back.stilevo.project.api.config;

import com.stilevo.store.back.stilevo.project.api.domain.entity.Cart;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Product;
import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import com.stilevo.store.back.stilevo.project.api.domain.entity.User;
import com.stilevo.store.back.stilevo.project.api.domain.entity.embeddable.Endereco;
import com.stilevo.store.back.stilevo.project.api.domain.enums.Size;
import com.stilevo.store.back.stilevo.project.api.domain.enums.UserRole;
import com.stilevo.store.back.stilevo.project.api.domain.repository.ProductRespository;
import com.stilevo.store.back.stilevo.project.api.domain.repository.ProductVariationRepository;
import com.stilevo.store.back.stilevo.project.api.domain.repository.UserRepository;
import com.stilevo.store.back.stilevo.project.api.mapper.ProductVariationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class TestConfig implements CommandLineRunner {

    @Autowired
    private ProductRespository productRespository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        Product p1 = new Product(null, "Camisa Básica", "Camisa de algodão simples", new BigDecimal("49.90"));
        Product p2 = new Product(null, "Calça Jeans", "Calça jeans muito confortavel", new BigDecimal("129.90"));
        Product p3 = new Product(null, "Tênis Esportivo", "Tênis confortável para caminhada", new BigDecimal("199.90"));

        productRespository.saveAll(List.of(p1, p2, p3));

        ProductVariation variation1 = new ProductVariation(null, "Azul", 10, "https://imagem.com/camisa-azul.jpg", p1);
        ProductVariation variation2 = new ProductVariation(null, "Preto", 5, "https://imagem.com/camisa-preta.jpg", p1);
        ProductVariation variation3 = new ProductVariation(null, "Branco", 8, "https://imagem.com/camisa-branca.jpg", p1);

        productVariationRepository.saveAll(List.of(variation1, variation2, variation3));

        // Criando instâncias de Endereco
        Endereco endereco1 = new Endereco("12345-678", "Rua das Flores", "Apto 101", "Bairro das Palmeiras", "CidadeX", "EstadoY", 123, "Próximo ao supermercado");
        Endereco endereco2 = new Endereco("98765-432", "Avenida Brasil", "de 1000 a 2000 - lado par", "Bairro Central", "CidadeZ", "EstadoW", 456, "Em frente ao parque");

        // Criando instâncias de User sem carrinho (Cart)
        User user1 = new User(null, "joao_silva", "joao.silva@email.com", "senha123", endereco1, UserRole.USER, null);
        User user2 = new User(null, "maria_oliveira", "maria.oliveira@email.com", "senha456", endereco2, UserRole.USER, null);

        Cart cart1 = new Cart();
        cart1.setUser(user1);
        user1.setCart(cart1);

        Cart cart2 = new Cart();
        cart2.setUser(user2);
        user2.setCart(cart2);

        user1.setPassword(new BCryptPasswordEncoder().encode(user1.getPassword()));
        user2.setPassword(new BCryptPasswordEncoder().encode(user2.getPassword()));

        userRepository.saveAll(List.of(user1, user2));
    }
}
