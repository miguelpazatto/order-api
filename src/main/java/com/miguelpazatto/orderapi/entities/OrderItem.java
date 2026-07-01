package com.miguelpazatto.orderapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private Integer quantity;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public BigDecimal getSubTotal() {
        if (this.price == null || this.quantity == null) {
            throw new IllegalStateException("Erro interno: Tentativa de calcular subtotal de um item com preço ou quantidade nulos. ID do Produto" + (this.product != null ? this.product.getId() : "Desconhecido"));
        }
        return this.price.multiply(BigDecimal.valueOf(quantity));
    }

}
