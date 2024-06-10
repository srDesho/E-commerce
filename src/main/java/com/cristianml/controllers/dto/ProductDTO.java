package com.cristianml.controllers.dto;

import com.cristianml.models.CategoryModel;
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

public class ProductDTO {

    private Integer id;
    @NotEmpty(message = "is empty.")
    private String name;
    private String slug;
    @NotEmpty(message = "is empty.")
    private String description;
    @NotNull(message = "Can't be null.")
    private BigDecimal price;
    private String image;
    @NotNull(message = "can't be null")
    private Integer stock;
    private BigDecimal discount;
    private BigDecimal discountPrice;
    private Boolean isActive;
    private CategoryModel category;

}
