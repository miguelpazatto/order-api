package com.miguelpazatto.orderapi.services;

import com.miguelpazatto.orderapi.dto.ProductRequestDTO;
import com.miguelpazatto.orderapi.dto.ProductResponseDTO;
import com.miguelpazatto.orderapi.entities.Product;
import com.miguelpazatto.orderapi.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<ProductResponseDTO> findAll() {
        List<Product> products = repository.findAll();
        return products.stream().map(ProductResponseDTO::new).toList();
    }

    public ProductResponseDTO findById(Long id) {
        Optional<Product> product = repository.findById(id);
        return product.map(ProductResponseDTO::new).orElseThrow();
    }

    @Transactional
    public ProductResponseDTO insert(ProductRequestDTO newProduct) {
        Product product = new Product();
        product.setName(newProduct.name());
        product.setPrice(newProduct.price());
        product.setAvailableStock(newProduct.availableStock());

        Product savedProduct = repository.save(product);
        return new ProductResponseDTO(savedProduct);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public ProductResponseDTO update(Long id, ProductRequestDTO toBeChangedProduct) {
        Product product = repository.getReferenceById(id);
        updateData(product, toBeChangedProduct);
        return new ProductResponseDTO(repository.save(product));
    }

    private void updateData(Product product, ProductRequestDTO productNewData) {
        product.setName(productNewData.name());
        product.setPrice(productNewData.price());
        product.setAvailableStock(productNewData.availableStock());
    }


}
