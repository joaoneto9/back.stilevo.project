package com.stilevo.store.back.stilevo.project.api.controller;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.AddToCartRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.CartItemResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.CartResponseDTO;
import com.stilevo.store.back.stilevo.project.api.mapper.CartMapper;
import com.stilevo.store.back.stilevo.project.api.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CartResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.findById(id));
    }

    @PostMapping(value = "/{id}/product")
    public ResponseEntity<CartItemResponseDTO> addProduct(
            @PathVariable Long id,
            @RequestBody @Valid AddToCartRequestDTO addToCartRequestDTO
    ) {
        return ResponseEntity.ok(cartService.addProductToCart(id, addToCartRequestDTO)); // quero que retorne apenas o CarItem, que mudou
    }

    @PatchMapping(value = "/{id}/product/{cartItemId}/decrease")
    public ResponseEntity<CartItemResponseDTO> decreaseProduct(
            @PathVariable Long id,
            @PathVariable Long cartItemId
    ) {
        return ResponseEntity.ok(cartService.decreaseProduct(id, cartItemId));
    }

    @PatchMapping(value = "/{id}/product/{cartItemId}/increase")
    public ResponseEntity<CartItemResponseDTO> increaseProduct(
            @PathVariable Long id,
            @PathVariable Long cartItemId
    ) {
        return ResponseEntity.ok(cartService.increaseProduct(id, cartItemId));
    }


    @DeleteMapping(value = "/{id}/product/{cartItemId}")
    public ResponseEntity<Void> removeProduct(
            @PathVariable Long id,
            @PathVariable Long cartItemId
    ) {
        return ResponseEntity.ok(cartService.removeProduct(id, cartItemId));
    }

}
