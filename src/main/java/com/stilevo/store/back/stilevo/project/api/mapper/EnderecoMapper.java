package com.stilevo.store.back.stilevo.project.api.mapper;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.EnderecoRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.EnderecoViacepRequest;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.EnderecoResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.embeddable.Endereco;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper extends GeneralMapper<Endereco, EnderecoRequestDTO, EnderecoResponseDTO>{
    EnderecoResponseDTO toResponse(EnderecoViacepRequest enderecoViacepRequest);
}
