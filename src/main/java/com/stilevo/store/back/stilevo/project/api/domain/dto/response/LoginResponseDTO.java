package com.stilevo.store.back.stilevo.project.api.domain.dto.response;

public record LoginResponseDTO(UserResponseDTO user, String token) {
}
