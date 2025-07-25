package com.stilevo.store.back.stilevo.project.api.domain.dto.response;

import com.stilevo.store.back.stilevo.project.api.domain.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {

    private Long id;
    private ProductVariationResponseDTO productVariation;
    private Size size;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
