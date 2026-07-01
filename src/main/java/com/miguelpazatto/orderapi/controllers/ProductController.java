package com.miguelpazatto.orderapi.controllers;

import com.miguelpazatto.orderapi.dto.ProductRequestDTO;
import com.miguelpazatto.orderapi.dto.ProductResponseDTO;
import com.miguelpazatto.orderapi.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        List<ProductResponseDTO> products = service.findAll();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        ProductResponseDTO product = service.findById(id);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> insert(@RequestBody @Valid ProductRequestDTO newProduct) {
        ProductResponseDTO savedProduct = service.insert(newProduct);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedProduct.id()).toUri();
        return ResponseEntity.created(uri).body(savedProduct);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ProductRequestDTO toBeChangedProduct) {
        ProductResponseDTO changedProduct = service.update(id, toBeChangedProduct);
        return ResponseEntity.ok().body(changedProduct);
    }

}
