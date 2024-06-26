package com.cristianml.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

    // @Lob indicates that the field should be persisted as a large object in the database.
    // @Column(columnDefinition = "TEXT") specifies that the column should be of type TEXT in the database.
    @Lob
    @Column(columnDefinition = "TEXT")
    @NotEmpty(message = "is empty.")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Can't be null.")
    private BigDecimal price;
    private String image;
    @NotNull(message = "can't be null")
    private Integer stock;

    private BigDecimal discount;
    private BigDecimal discountPrice;
    private Boolean isActive;

    @ManyToOne(targetEntity = CategoryModel.class)
    @JoinColumn(name = "id_category")
    private CategoryModel category;

    @OneToMany(mappedBy = "product")
    private List<CartItemModel> cartItem = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<OrderItemModel> orderItem = new ArrayList<>();
}
