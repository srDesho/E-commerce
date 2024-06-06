package com.cristianml.repository;

import com.cristianml.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Integer> {

    // This is to check if there is a slug registered in the database.
    boolean existsBySlug(String slug);

}
