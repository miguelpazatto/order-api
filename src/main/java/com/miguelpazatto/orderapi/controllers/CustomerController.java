package com.miguelpazatto.orderapi.controllers;

import com.miguelpazatto.orderapi.dto.CustomerRequestDTO;
import com.miguelpazatto.orderapi.dto.CustomerResponseDTO;
import com.miguelpazatto.orderapi.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> findAll() {
        List<CustomerResponseDTO> customers = service.findAll();
        return ResponseEntity.ok().body(customers);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable Long id) {
        CustomerResponseDTO customer = service.findById(id);
        return ResponseEntity.ok().body(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> insert(@RequestBody @Valid CustomerRequestDTO newCustomer) {
        CustomerResponseDTO savedCustomer = service.insert(newCustomer);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCustomer.id()).toUri();
        return ResponseEntity.created(uri).body(savedCustomer);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id, @RequestBody @Valid CustomerRequestDTO toBeChangedCustomer) {
        CustomerResponseDTO changedCustomer = service.update(id, toBeChangedCustomer);
        return ResponseEntity.ok().body(changedCustomer);
    }

}
