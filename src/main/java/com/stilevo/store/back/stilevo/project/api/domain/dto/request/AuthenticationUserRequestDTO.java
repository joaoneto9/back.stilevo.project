package com.stilevo.store.back.stilevo.project.api.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationUserRequestDTO(
        @NotBlank String email,

        @NotBlank String password
) { }
