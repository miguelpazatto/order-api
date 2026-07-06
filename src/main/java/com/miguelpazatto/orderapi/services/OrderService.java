package com.miguelpazatto.orderapi.services;

import com.miguelpazatto.orderapi.dto.OrderItemRequestDTO;
import com.miguelpazatto.orderapi.dto.OrderRequestDTO;
import com.miguelpazatto.orderapi.dto.OrderResponseDTO;
import com.miguelpazatto.orderapi.entities.Customer;
import com.miguelpazatto.orderapi.entities.Order;
import com.miguelpazatto.orderapi.entities.OrderItem;
import com.miguelpazatto.orderapi.entities.Product;
import com.miguelpazatto.orderapi.entities.enums.OrderStatus;
import com.miguelpazatto.orderapi.repositories.CustomerRepository;
import com.miguelpazatto.orderapi.repositories.OrderRepository;
import com.miguelpazatto.orderapi.repositories.ProductRepository;
import com.miguelpazatto.orderapi.services.exceptions.InsufficientStockException;
import com.miguelpazatto.orderapi.services.exceptions.InvalidOrderStatusException;
import com.miguelpazatto.orderapi.services.exceptions.ProductInativeException;
import com.miguelpazatto.orderapi.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final CustomerRepository customerRepository;

    public List<OrderResponseDTO> findAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderResponseDTO::new).toList();
    }

    public OrderResponseDTO findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(OrderResponseDTO::new).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public OrderResponseDTO insert(OrderRequestDTO newOrder) {

        Map<Long, Integer> groupedItems = newOrder.orderItems().stream()
                .collect(Collectors.toMap(
                        OrderItemRequestDTO::productId,
                        OrderItemRequestDTO::quantity,
                        Integer::sum
                ));

        List<Long> productsIds = new ArrayList<>(groupedItems.keySet());
        List<Product> products = productRepository.findAllById(productsIds);

        Map<Long, Product> productsMap = products.stream().collect(Collectors.toMap(Product::getId, p -> p));

        groupedItems.forEach((productId, quantity) -> {

            Product product = productsMap.get(productId);

            if (product == null) {
                throw new ResourceNotFoundException(productId);
            }

            if (quantity > product.getAvailableStock()) {
                throw new InsufficientStockException("Estoque do Produto com ID " + product.getId() + " insuficiente.");
            }

        });

        Order order = new Order();
        order.setOrderStatus(OrderStatus.PENDING);

        Customer customer = customerRepository.findById(newOrder.customerId()).orElseThrow(() -> new ResourceNotFoundException(newOrder.customerId()));

        order.setCustomer(customer);

        groupedItems.forEach((productId, quantity) -> {

            Product product = productsMap.get(productId);

            product.setAvailableStock(product.getAvailableStock() - quantity);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);

            order.getOrderItems().add(orderItem);
        });

        orderRepository.save(order);
        return new OrderResponseDTO(order);
    }

    @Transactional
    public OrderResponseDTO updateStatus(Long id, OrderStatus newStatus) {

        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        if (order.getOrderStatus() == OrderStatus.CANCELED || order.getOrderStatus() == OrderStatus.REFUSED) {
            throw new InvalidOrderStatusException("Este pedido já foi encerrado e não pode ser alterado");
        }

        if (newStatus == OrderStatus.CANCELED || newStatus == OrderStatus.REFUSED) {

            for (OrderItem orderItem : order.getOrderItems()) {
                Product product = orderItem.getProduct();
                product.setAvailableStock(product.getAvailableStock() + orderItem.getQuantity());
            }

        }

        order.setOrderStatus(newStatus);

        return new OrderResponseDTO(orderRepository.save(order));

    }


}
