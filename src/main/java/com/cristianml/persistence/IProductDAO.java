package com.cristianml.persistence;

import com.cristianml.models.ProductModel;

import java.util.List;
import java.util.Optional;

public interface IProductDAO {

    List<ProductModel> listProducts();

    Optional<ProductModel> findById(Integer id);
    void save(ProductModel product);

    void deleteById(Integer id);

    // Check if exists relation with a category
    boolean checkRelationWithCategory(Integer idCategory);

    // Get all active products
    List<ProductModel> getAllActiveProducts();

    // Get active products by category
    List<ProductModel> getActiveProductsByCategory(String category);

}
