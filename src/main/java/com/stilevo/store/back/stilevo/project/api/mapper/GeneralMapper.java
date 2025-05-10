package com.stilevo.store.back.stilevo.project.api.mapper;

public interface GeneralMapper<Entity, EntityRequestDTO, EntityResponseDTO> {
    Entity toEntity(EntityRequestDTO entityRequestDTO);
    EntityResponseDTO toResponse(Entity entity);
}
