package com.stilevo.store.back.stilevo.project.api.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDTO {

    private ProductVariationResponseDTO productVariation;

    private int quantity;
}
