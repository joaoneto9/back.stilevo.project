package com.stilevo.store.back.stilevo.project.api.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartRequestDTO {

    private Long clientId;

    private Long productVariationId;
}
