package com.miguelpazatto.orderapi.dto;

import com.miguelpazatto.orderapi.entities.Product;

import java.math.BigDecimal;

public record ProductResponseDTO(Long id, String name, BigDecimal price, Integer availableStock) {

    public ProductResponseDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getAvailableStock()
        );
    }

}
