package com.miguelpazatto.orderapi.services;

import com.miguelpazatto.orderapi.dto.CustomerRequestDTO;
import com.miguelpazatto.orderapi.dto.CustomerResponseDTO;
import com.miguelpazatto.orderapi.entities.Customer;
import com.miguelpazatto.orderapi.repositories.CustomerRepository;
import com.miguelpazatto.orderapi.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public List<CustomerResponseDTO> findAll() {
        List<Customer> customers = repository.findAll();
        return customers.stream().map(CustomerResponseDTO::new).toList();
    }

    public CustomerResponseDTO findById(Long id) {
        Optional<Customer> customer = repository.findById(id);
        return customer.map(CustomerResponseDTO::new).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public CustomerResponseDTO insert(CustomerRequestDTO newCustomer) {
        Customer customer = new Customer();
        customer.setName(newCustomer.name());
        customer.setEmail(newCustomer.email());

        Customer savedCustomer = repository.save(customer);
        return new CustomerResponseDTO(savedCustomer);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public CustomerResponseDTO update(Long id, CustomerRequestDTO toBeChangedCustomer) {
        try {
            Customer customer = repository.getReferenceById(id);
            updateData(customer, toBeChangedCustomer);
            return new CustomerResponseDTO(repository.save(customer));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Customer customer, CustomerRequestDTO customerNewData) {
        customer.setName(customerNewData.name());
        customer.setEmail(customerNewData.email());
    }

}
