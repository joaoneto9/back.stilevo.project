package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.exception.NotFoundException;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.AddToCartRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Cart;
import com.stilevo.store.back.stilevo.project.api.domain.entity.CartItem;
import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import com.stilevo.store.back.stilevo.project.api.domain.repository.CartItemRepository;
import com.stilevo.store.back.stilevo.project.api.domain.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductVariationService productVariationService;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, ProductVariationService productVariationService, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productVariationService = productVariationService;
        this.cartItemRepository = cartItemRepository;
    }

    public Cart findById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("carrinho do cliente com id:" + id + " nao encontrado"));
    }

    @Transactional
    public CartItem addProductToCart(AddToCartRequestDTO addToCartRequestDTO) {
        Cart cart = findById(addToCartRequestDTO.getClientId());

        ProductVariation productVariation = productVariationService.findById(addToCartRequestDTO.getProductVariationId()); // acha pelo id

        CartItem cartItem = cart.addProduct(productVariation, addToCartRequestDTO.getSize()); // adiciona o produto no cart

        return cartItemRepository.save(cartItem);
    }

    @Transactional
    public CartItem removeProduct(Long id, int posicao) {
        Cart cart = findById(id);

        CartItem cartItem = cart.removeProduct(posicao);

        if (cartItem == null)
            throw new NotFoundException("produto no carrinho nao encontradi");

        cartRepository.save(cart); // atualiza

        return cartItem;
    }

    @Transactional
    public CartItem decreaseProduct(Long id, int posicao) {
        Cart cart = findById(id);

        CartItem cartItem = cart.decreaseProduct(posicao);

        if (cartItem == null)
            throw new NotFoundException("produto no carrinho nao encontradi");

        cartRepository.save(cart); // atualiza

        return cartItem;
    }

    @Transactional
    public CartItem increaseProduct(Long id, int posicao) {
        Cart cart = findById(id);

        CartItem cartItem = cart.increaseProduct(posicao);

        if (cartItem == null)
            throw new NotFoundException("produto no carrinho nao encontradi");

        cartRepository.save(cart); // atualiza

        return cartItem;
    }

    protected CartItem findCartItemByPosition(Long userId, int posicao) {
        return findById(userId).getCartItems().get(posicao - 1); // ta feio, depois vou abstrair isso para seguir o "design da informacao"
     }
}
