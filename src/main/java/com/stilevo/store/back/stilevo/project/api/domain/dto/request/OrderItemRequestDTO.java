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

    @NotNull
    @Min(1)
    private Long userId;

    @NotNull
    @Min(1)
    private Long CartItemPosition;
}
