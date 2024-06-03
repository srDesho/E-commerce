package com.cristianml.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "products")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "is empty.")
    private String name;
    private String slug;
    @NotEmpty(message = "is empty.")
    private String description;
    @Column(nullable = false)
    @NotNull(message = "Can't be null.")
    private BigDecimal price;
    private String image;
    private int stock;
    private Double discount;
    private Boolean isActive;

    @ManyToOne(targetEntity = CategoryModel.class)
    @JoinColumn(name = "id_category")
    private CategoryModel category;
}
