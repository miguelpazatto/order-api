package com.miguelpazatto.orderapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record OrderRequestDTO(
        @NotNull(message = "O ID é obrigatório") @Positive(message = "ID tem que ser maior que zero") Long customerId,

        @NotEmpty(message = "A lista de itens do pedido não pode ser vazia ou nula") @Valid List<OrderItemRequestDTO> orderItems) {

}
