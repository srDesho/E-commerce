package com.cristianml.persistence.impl;

import com.cristianml.models.CategoryModel;
import com.cristianml.persistence.ICategoryDAO;
import com.cristianml.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component // We define this class as a bean so that it can be injected from another class.
public class CategoryDAOImpl implements ICategoryDAO {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryModel> listCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Optional<CategoryModel> findById(Integer id) {
        return this.categoryRepository.findById(id);
    }

    @Override
    public void save(CategoryModel category) {
        this.categoryRepository.save(category);
    }

    @Override
    public void deleteById(Integer id) {
        this.categoryRepository.deleteById(id);
    }

    // Search by slug
    // This method will check if a slug already exists in the database.
    // The method should be created in the ICategoryDAO.
    @Override
    public boolean existsBySlug(String slug) {
        if (this.categoryRepository.existsBySlug(slug)) {
            return false; // If it exists, the process to create a new record will not be allowed to continue.
        } else {
            return true;
        }
    }

    @Override
    public List<CategoryModel> getActiveCategories(Boolean isActive) {
        return this.categoryRepository.findAllCategoryModelByIsActive(isActive);
    }
}
