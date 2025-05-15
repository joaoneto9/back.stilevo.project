package com.stilevo.store.back.stilevo.project.api.domain.dto.response;

import com.stilevo.store.back.stilevo.project.api.domain.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDTO {

    private Long id; // para sinalizar o numero do pedido

    private ProductVariationResponseDTO productVariation;

    private int quantity;

    private Size size;

}
