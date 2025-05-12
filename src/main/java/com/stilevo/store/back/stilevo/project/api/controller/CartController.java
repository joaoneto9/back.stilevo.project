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
@RequestMapping(value = "api/cart")
public class CartController {

    private CartService cartService;
    private CartMapper cartMapper;

    public CartController(CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    @GetMapping(value = "/GET/{id}")
    public ResponseEntity<CartResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cartMapper.toResponse(cartService.findById(id)));
    }

    @PutMapping(value = "/PUT/product")
    public ResponseEntity<CartItemResponseDTO> addProduct(@RequestBody @Valid AddToCartRequestDTO addToCartRequestDTO) {
        return ResponseEntity.ok(cartMapper.toResponse(cartService.addProductToCart(addToCartRequestDTO))); // quero que retorne apenas o CarItem, que mudou
    }

    @DeleteMapping(value = "/DECREASE/{id}/product/{posicao}")
    public ResponseEntity<CartItemResponseDTO> decreaseProduct(
            @PathVariable Long id,
            @PathVariable int posicao
    ) {
        return ResponseEntity.ok(cartMapper.toResponse(cartService.decreaseProduct(id, posicao)));
    }

    @PutMapping(value = "/INCREASE/{id}/product/{posicao}")
    public ResponseEntity<CartItemResponseDTO> increaseProduct(
            @PathVariable Long id,
            @PathVariable int posicao
    ) {
        return ResponseEntity.ok(cartMapper.toResponse(cartService.increaseProduct(id, posicao)));
    }


    @DeleteMapping(value = "/DELETE/{id}/product/{posicao}")
    public ResponseEntity<CartItemResponseDTO> removeProduct(
            @PathVariable Long id,
            @PathVariable int posicao
    ) {
        return ResponseEntity.ok(cartMapper.toResponse(cartService.removeProduct(id, posicao)));
    }

}
