package com.stilevo.store.back.stilevo.project.api.mapper;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.ProductRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.ProductResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Product;

public interface ProductMapper extends GeneralMapper<Product, ProductRequestDTO, ProductResponseDTO> {
}
