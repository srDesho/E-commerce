package com.cristianml.service;

import com.cristianml.models.ProductModel;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<ProductModel> listProducts();

    Optional<ProductModel> findById(Integer id);
    void save(ProductModel product);

    void deleteById(Integer id);

    // Check if exists relation with a category
    boolean checkRelationWithCategory(Integer idCategory);

}
