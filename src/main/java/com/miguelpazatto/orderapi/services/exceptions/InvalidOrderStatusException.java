package com.miguelpazatto.orderapi.services.exceptions;

public class InvalidOrderStatusException extends BusinessException{
    public InvalidOrderStatusException(String message) {
        super(message);
    }
}
