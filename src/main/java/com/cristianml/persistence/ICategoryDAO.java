package com.cristianml.persistence;

import com.cristianml.models.CategoryModel;

import java.util.List;
import java.util.Optional;

public interface ICategoryDAO {

    List<CategoryModel> listCategories();

    Optional<CategoryModel> findById(Integer id);
    void save(CategoryModel category);

    void deleteById(Integer id);

    boolean existsBySlug(String slug);

}
