package com.rocha.fullstack.dto;

import com.rocha.fullstack.models.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithCart {
    private Product product;
    private String cartId;
}
