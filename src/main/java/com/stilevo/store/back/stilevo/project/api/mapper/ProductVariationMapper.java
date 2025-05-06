package com.stilevo.store.back.stilevo.project.api.mapper;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.ProductVariationRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.ProductVariationResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductVariationMapper extends GeneralMapper<ProductVariation, ProductVariationRequestDTO, ProductVariationResponseDTO> {
}
