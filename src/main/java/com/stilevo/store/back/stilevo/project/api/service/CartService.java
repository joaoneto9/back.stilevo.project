package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.AddToCartRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import com.stilevo.store.back.stilevo.project.api.exception.NotFoundException;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Cart;
import com.stilevo.store.back.stilevo.project.api.domain.entity.CartItem;
import com.stilevo.store.back.stilevo.project.api.domain.repository.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductVariationService productVariationService;

    public CartService(CartRepository cartRepository, ProductVariationService productVariationService) {
        this.cartRepository = cartRepository;
        this.productVariationService = productVariationService;
    }

    @Transactional(readOnly = true)
    public Cart findById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("carrinho do cliente com id:" + id + " nao encontrado"));
    }

    @Transactional
    public CartItem addProductToCart(Long id, AddToCartRequestDTO addToCartRequestDTO) {
        try {
            Cart cart = cartRepository.getReferenceById(id);

            ProductVariation productVariation = productVariationService.findById(addToCartRequestDTO.getProductVariationId()); // acha pelo id

            CartItem cartItem = cart.getCartItems()
                    .stream()
                    .filter(x ->
                        x.getProductVariation().equals(productVariation) && x.getSize().equals(addToCartRequestDTO.getSize())
                    ) // nao precisa criar objetos de CartItem avulsos, ajustar isso depois
                    .findFirst()
                    .orElse(new CartItem(null, cart, productVariation, productVariation.getPriceProduct(), 1, addToCartRequestDTO.getSize()));

            if (cartItem.getId() != null) {
                cartItem.addQuantity();
                cartRepository.save(cart);
                return cartItem;
            }

            cart.getCartItems().add(cartItem);

            return cartRepository.save(cart).getCartItems().get(cart.getCartItems().size() - 1); // garante que e o ultimo adicionado
        } catch (EntityNotFoundException exception) {
            throw new NotFoundException("carrinho do cliente com id:" + id + " nao encontrado");
        }
    }

    @Transactional
    public Void removeProduct(Long id, Long cartItemId) {
        try {
            Cart cart = cartRepository.getReferenceById(id);

            cart.getCartItems().remove(cartItemFilter(cart, cartItemId));

            cartRepository.save(cart); // atualiza

            return null;
        } catch (EntityNotFoundException exception) {
            throw new NotFoundException("carrinho do cliente com id:" + id + " nao encontrado");
        }
    }

    private CartItem cartItemFilter(Cart cart, Long cartItemId) {
        return cart.getCartItems()
                .stream()
                .filter(x -> x.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("produto no carrinho nao encontrado"));
    }

    @Transactional
    public CartItem decreaseProduct(Long id, Long cartItemId) {
        try {
            Cart cart = cartRepository.getReferenceById(id);

            CartItem cartItem = cartItemFilter(cart, cartItemId);

            cartItem.decreaseQuantity();

            if (cartItem.getQuantity() == 0) {
               cart.getCartItems().remove(cartItem);
            }

            cartRepository.save(cart); // atualiza

            return cartItem;
        } catch (EntityNotFoundException exception) {
            throw new NotFoundException("carrinho do cliente com id:" + id + " nao encontrado");
        }
    }

    @Transactional
    public CartItem increaseProduct(Long id, Long cartItemId) {
        try {
            Cart cart = cartRepository.getReferenceById(id);

            CartItem cartItem = cartItemFilter(cart, cartItemId);

            cartItem.addQuantity();

            cartRepository.save(cart); // atualiza

            return cartItem;
        } catch (EntityNotFoundException exception) {
            throw new NotFoundException("carrinho do cliente com id:" + id + " nao encontrado");
        }

    }

    protected CartItem findCartItemAtUserCart(Long userId, Long cartItemId) {
        return cartItemFilter(findById(userId), cartItemId);
    }
}
