package com.stilevo.store.back.stilevo.project.api.mapper;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.ProductRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.ProductVariationRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.ProductResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.ProductVariationResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Product;
import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductVariationMapper {
    ProductVariation toEntity(ProductVariationRequestDTO productVariationRequestDTO);
    ProductVariationResponseDTO toResponse(ProductVariation productVariation);
}
