package com.miguelpazatto.orderapi.dto;

import com.miguelpazatto.orderapi.entities.Customer;

import java.util.List;

public record CustomerResponseDTO(Long id, String name, String email, List<OrderResponseDTO> orders) {

    public CustomerResponseDTO(Customer customer) {
        this(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getOrders().stream().map(OrderResponseDTO::new).toList()
        );
    }

}
