package com.miguelpazatto.orderapi.services.exceptions;

public class InsufficientStockException extends BusinessException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
