package com.stilevo.store.back.stilevo.project.api.domain.dto.request;

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

    @NotNull
    @Min(1)
    private Long clientId;

    @NotNull
    @Min(1)
    private Long productVariationId;
}
