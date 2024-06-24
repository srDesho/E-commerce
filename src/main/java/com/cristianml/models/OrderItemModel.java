package com.cristianml.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.service.annotation.GetExchange;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_item")
public class OrderItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private BigDecimal price;

    @ManyToOne()
    @JoinColumn(name = "order_id", nullable = false)
    private OrderModel order;

    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    private ProductModel product;
}
