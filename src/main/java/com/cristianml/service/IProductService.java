package com.cristianml.service;

import com.cristianml.controllers.dto.ProductDTO;
import com.cristianml.models.CategoryModel;
import com.cristianml.models.ProductModel;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<ProductModel> listProducts();

    Optional<ProductModel> findById(Integer id);
    void save(ProductModel product);

    void deleteById(Integer id);

    // Check if exists relation with a category
    boolean checkRelationWithCategory(Integer idCategory);

    BigDecimal calculateDiscountPrice(BigDecimal price, BigDecimal discount);

    // Get all active products
    List<ProductModel> getAllActiveProducts();

    // Get active products by category
    List<ProductModel> getActiveProductsByCategory(String category);

}
