package com.stilevo.store.back.stilevo.project.api.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GeneralMapper<Entity, EntityRequestDTO, EntityResponseDTO> {

    Entity toEntity(EntityRequestDTO entityRequestDTO);
    EntityResponseDTO toResponse(Entity entity);

}
