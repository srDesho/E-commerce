package com.cristianml.controllers.dto;

import com.cristianml.models.ProductModel;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CategoryDTO {

    private Integer id;
    @NotEmpty(message = "Can't be empty.")
    private String name;
    // This slug is for validating duplicated registers in DB.
    private String slug;
    private String image;
    private List<ProductModel> productList = new ArrayList<>();

}
