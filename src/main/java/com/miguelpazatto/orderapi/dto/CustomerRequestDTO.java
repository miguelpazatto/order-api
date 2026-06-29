package com.miguelpazatto.orderapi.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerRequestDTO(@NotBlank(message = "Favor inserir nome do cliente") String name,
                                 @NotBlank(message = "Favor inserir email do cliente") String email) {
}
