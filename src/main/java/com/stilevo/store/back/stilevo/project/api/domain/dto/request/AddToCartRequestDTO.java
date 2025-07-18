package com.stilevo.store.back.stilevo.project.api.domain.dto.request;

import com.stilevo.store.back.stilevo.project.api.domain.enums.Size;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartRequestDTO {

    @NotNull(message = "erro, ID do produto nulo")
    @Min(value = 1, message = "erro, ID do produto invalido.")
    private Long productVariationId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Size size;
}
