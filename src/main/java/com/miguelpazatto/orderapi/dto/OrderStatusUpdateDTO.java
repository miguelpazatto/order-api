package com.miguelpazatto.orderapi.dto;

import com.miguelpazatto.orderapi.entities.enums.OrderStatus;

public record OrderStatusUpdateDTO(OrderStatus status) {
}
