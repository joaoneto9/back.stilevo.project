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
    private final CartMapper cartMapper;

    public CartController(CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CartResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cartMapper.toResponse(cartService.findById(id)));
    }

    @PostMapping(value = "/{id}/product")
    public ResponseEntity<CartItemResponseDTO> addProduct(
            @PathVariable Long id,
            @RequestBody @Valid AddToCartRequestDTO addToCartRequestDTO
    ) {
        return ResponseEntity.ok(cartMapper.toResponse(cartService.addProductToCart(id, addToCartRequestDTO))); // quero que retorne apenas o CarItem, que mudou
    }

    @PatchMapping(value = "/{id}/product/{cartItemId}/decrease")
    public ResponseEntity<CartItemResponseDTO> decreaseProduct(
            @PathVariable Long id,
            @PathVariable Long cartItemId
    ) {
        return ResponseEntity.ok(cartMapper.toResponse(cartService.decreaseProduct(id, cartItemId)));
    }

    @PatchMapping(value = "/{id}/product/{cartItemId}/increase")
    public ResponseEntity<CartItemResponseDTO> increaseProduct(
            @PathVariable Long id,
            @PathVariable Long cartItemId
    ) {
        return ResponseEntity.ok(cartMapper.toResponse(cartService.increaseProduct(id, cartItemId)));
    }


    @DeleteMapping(value = "/{id}/product/{cartItemId}")
    public ResponseEntity<Void> removeProduct(
            @PathVariable Long id,
            @PathVariable Long cartItemId
    ) {
        return ResponseEntity.ok(cartService.removeProduct(id, cartItemId));
    }

}
