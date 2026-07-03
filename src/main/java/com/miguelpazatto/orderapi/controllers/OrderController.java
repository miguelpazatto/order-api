package com.miguelpazatto.orderapi.controllers;

import com.miguelpazatto.orderapi.dto.OrderRequestDTO;
import com.miguelpazatto.orderapi.dto.OrderResponseDTO;
import com.miguelpazatto.orderapi.dto.OrderStatusUpdateDTO;
import com.miguelpazatto.orderapi.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> findAll() {
        List<OrderResponseDTO> orders = service.findAll();
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id) {
        OrderResponseDTO order = service.findById(id);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> insert(@RequestBody @Valid OrderRequestDTO newOrder) {
        OrderResponseDTO savedOrder = service.insert(newOrder);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id").buildAndExpand(savedOrder.id()).toUri();
        return ResponseEntity.created(uri).body(savedOrder);
    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(@PathVariable Long id, @RequestBody OrderStatusUpdateDTO newStatus) {
        OrderResponseDTO updatedOrder = service.updateStatus(id, newStatus.status());
        return ResponseEntity.ok().body(updatedOrder);
    }

}
