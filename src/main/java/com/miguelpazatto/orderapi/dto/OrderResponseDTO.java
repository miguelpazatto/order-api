package com.miguelpazatto.orderapi.dto;

import com.miguelpazatto.orderapi.entities.Customer;
import com.miguelpazatto.orderapi.entities.Order;
import com.miguelpazatto.orderapi.entities.enums.OrderStatus;

import java.util.List;

public record OrderResponseDTO(Long id, OrderStatus orderStatus, String orderDescription, Long customerId,
                               String customerName, List<OrderItemResponseDTO> orderItems) {

    public OrderResponseDTO(Order order) {
        this(
                order.getId(),
                order.getOrderStatus(),
                order.getOrderStatus().getDescription(),
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getOrderItems().stream().map(OrderItemResponseDTO::new).toList()
        );

    }

}
