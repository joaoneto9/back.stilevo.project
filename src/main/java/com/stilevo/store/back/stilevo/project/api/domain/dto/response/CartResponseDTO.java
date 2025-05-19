package com.stilevo.store.back.stilevo.project.api.domain.dto.response;

import lombok.*;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.validation.ObjectError;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {

    private List<CartItemResponseDTO> cartItems;

    public void setCartItems(List<CartItemResponseDTO> cartItems) {
        Collections.reverse(cartItems);
        this.cartItems = cartItems;
    }

}
