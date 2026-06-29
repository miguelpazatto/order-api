package com.miguelpazatto.orderapi.dto;

import com.miguelpazatto.orderapi.entities.OrderItem;
import com.miguelpazatto.orderapi.entities.Product;

import java.math.BigDecimal;

public record OrderItemResponseDTO(Long id, Integer quantity, BigDecimal price, Product product) {

    public OrderItemResponseDTO(OrderItem orderItem) {
        this(
                orderItem.getId(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getProduct()
        );
    }

}
