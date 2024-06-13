package com.cristianml.repository;

import com.cristianml.models.CategoryModel;
import com.cristianml.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Integer> {

    // This method is to check if there are products related with a Category
    boolean existsByCategory(CategoryModel category);

    // Get all active products
    List<ProductModel> findAllProductModelByIsActiveTrue();

    // Get all active products by category name
    @Query("SELECT p FROM ProductModel p WHERE p.isActive = true AND p.category.name = :categoryName")
    List<ProductModel> findAllActiveProductsByCategoryName(@Param("categoryName") String categoryName);

}
