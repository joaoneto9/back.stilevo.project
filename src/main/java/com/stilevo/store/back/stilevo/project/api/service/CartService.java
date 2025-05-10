package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.controller.exception.NotFoundException;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.AddToCartRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.CartResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Cart;
import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import com.stilevo.store.back.stilevo.project.api.domain.entity.User;
import com.stilevo.store.back.stilevo.project.api.domain.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductVariationService productVariationService;

    public CartService(CartRepository cartRepository, ProductVariationService productVariationService) {
        this.cartRepository = cartRepository;
        this.productVariationService = productVariationService;
    }

    public Cart findById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("carrinho do cliente com id:" + id + " nao encontrado"));
    }


    @Transactional
    public CartResponseDTO addProductToCart(AddToCartRequestDTO addToCartRequestDTO) {
        Cart cart = findById(addToCartRequestDTO.getClientId());

        ProductVariation productVariation = productVariationService.findById(addToCartRequestDTO.getProductVariationId()); // acha pelo id

        cart.addProduct(productVariation); // adiciona o produto no cart

        cartRepository.save(cart); // slava o Cart

        return new CartResponseDTO(cart.getCartItems()); // retorna um CartResponse
    }
}
