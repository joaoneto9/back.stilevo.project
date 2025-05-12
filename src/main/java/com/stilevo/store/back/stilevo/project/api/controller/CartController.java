package com.stilevo.store.back.stilevo.project.api.controller;

import com.stilevo.store.back.stilevo.project.api.service.CartService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/cart")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

//    @PutMapping(value = "/PUT/product")
//    public ResponseEntity<CartResponseDTO> addProduct(@RequestBody @Valid AddToCartRequestDTO addToCartRequestDTO) {
//        return ResponseEntity.ok(cartService.addProductToCart(addToCartRequestDTO));
//    }


}
