package com.stilevo.store.back.stilevo.project.api.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "erro, nome do produto vazio")
    private String name;

    @NotBlank(message = "erro, descricao do produto vazio")
    private String description;

    @NotNull(message = "erro, preco do produto nulo")
    @Min(value = 5, message = "erro, preco minimo do produto nao foi atingido.")
    private BigDecimal price;
}
