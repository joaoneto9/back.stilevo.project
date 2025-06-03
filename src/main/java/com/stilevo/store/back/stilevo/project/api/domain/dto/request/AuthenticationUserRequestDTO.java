package com.stilevo.store.back.stilevo.project.api.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationUserRequestDTO(
        @NotBlank(message = "erro, formato do email invalido") String email,
        @NotBlank(message = "erro, formato da senha invalido") String password
) { }
