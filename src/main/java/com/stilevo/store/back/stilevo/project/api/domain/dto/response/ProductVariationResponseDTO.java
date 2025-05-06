package com.stilevo.store.back.stilevo.project.api.domain.dto.response;

import com.stilevo.store.back.stilevo.project.api.domain.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariationResponseDTO {

    private Long id;

    private Size size;

    private String color;

    private Integer deposit;

    private String imageUrl;

    private ProductResponseDTO product; // quero que mostre o produto na minha Api;
}
