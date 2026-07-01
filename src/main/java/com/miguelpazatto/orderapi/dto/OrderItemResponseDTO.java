package com.miguelpazatto.orderapi.dto;

import com.miguelpazatto.orderapi.entities.OrderItem;
import com.miguelpazatto.orderapi.entities.Product;

import java.math.BigDecimal;

public record OrderItemResponseDTO(Long productId, String productName, BigDecimal subTotal, Integer quantity, BigDecimal price) {

    public OrderItemResponseDTO(OrderItem orderItem) {
        this(
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getSubTotal(),
                orderItem.getQuantity(),
                orderItem.getPrice()
        );
    }

}
