package com.stilevo.store.back.stilevo.project.api.domain.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {

    @NotNull(message = "erro, Id do usuario e nulo")
    @Min(value = 1, message = "erro, Id do usuario invalido")
    private Long UserId;

    @NotNull(message = "erro, Id do item no carrinho e nulo")
    @Min(value = 1, message = "erro, Id do item no carrinho invalido")
    private Long CartItemId;
}
