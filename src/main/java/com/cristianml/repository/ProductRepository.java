package com.cristianml.repository;

import com.cristianml.models.CategoryModel;
import com.cristianml.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Integer> {

    // This method is to check if there are products related with a Category
    boolean existsByCategory(CategoryModel category);

}
