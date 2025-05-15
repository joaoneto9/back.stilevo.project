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

    @NotBlank
    private String color;

    @NotNull
    private Integer deposit;

    private String imageUrl;

    @NotNull
    private Long productId;
}
