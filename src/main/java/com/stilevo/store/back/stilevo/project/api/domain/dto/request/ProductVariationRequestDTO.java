package com.stilevo.store.back.stilevo.project.api.domain.dto.request;

import com.stilevo.store.back.stilevo.project.api.domain.enums.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariationRequestDTO {

    @NotBlank(message = "erro, cor do produto variado esta vazia")
    private String color;

    @NotNull(message = "erro, numero do proudto variado no deposito esta nulo")
    private Integer deposit;

    private String imageUrl;

    @NotNull(message = "erro, Id do produto esta nulo")
    private Long productId;
}
