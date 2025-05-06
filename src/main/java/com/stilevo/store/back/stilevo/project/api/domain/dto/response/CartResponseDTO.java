package com.stilevo.store.back.stilevo.project.api.domain.dto.response;

import com.stilevo.store.back.stilevo.project.api.domain.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {

    private List<CartItem> cartItems;

}
