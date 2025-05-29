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

    private CartService cartService;
    private CartMapper cartMapper;

    public CartController(CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CartResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cartMapper.toResponse(cartService.findById(id)));
    }

    @PostMapping(value = "/{id}/products")
    public ResponseEntity<CartItemResponseDTO> addProduct(
            @PathVariable Long id,
            @RequestBody @Valid AddToCartRequestDTO addToCartRequestDTO
    ) {
        return ResponseEntity.ok(cartMapper.toResponse(cartService.addProductToCart(id, addToCartRequestDTO))); // quero que retorne apenas o CarItem, que mudou
    }

    @PatchMapping(value = "/{id}/product/{position}/decrease")
    public ResponseEntity<CartItemResponseDTO> decreaseProduct(
            @PathVariable Long id,
            @PathVariable int position
    ) {
        return ResponseEntity.ok(cartMapper.toResponse(cartService.decreaseProduct(id, position)));
    }

    @PatchMapping(value = "/{id}/product/{position}/increase")
    public ResponseEntity<CartItemResponseDTO> increaseProduct(
            @PathVariable Long id,
            @PathVariable int position
    ) {
        return ResponseEntity.ok(cartMapper.toResponse(cartService.increaseProduct(id, position)));
    }


    @DeleteMapping(value = "/{id}/product/{position}")
    public ResponseEntity<CartItemResponseDTO> removeProduct(
            @PathVariable Long id,
            @PathVariable int position
    ) {
        return ResponseEntity.ok(cartMapper.toResponse(cartService.removeProduct(id, position)));
    }

}
