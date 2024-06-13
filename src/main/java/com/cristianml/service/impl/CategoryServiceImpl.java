package com.cristianml.service.impl;

import com.cristianml.models.CategoryModel;
import com.cristianml.persistence.ICategoryDAO;
import com.cristianml.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class CategoryServiceImpl implements ICategoryService {



    private final ICategoryDAO categoryDAO;

    // Constructor injection
    @Autowired
    public CategoryServiceImpl(ICategoryDAO categoryDAO){
        this.categoryDAO = categoryDAO;
    }
    @Override
    public List<CategoryModel> listCategories() {
        return this.categoryDAO.listCategories();
    }

    @Override
    public Optional<CategoryModel> findById(Integer id) {
        return this.categoryDAO.findById(id);
    }

    @Override
    public void save(CategoryModel category) {
        this.categoryDAO.save(category);
    }

    @Override
    public void deleteById(Integer id) {
        this.categoryDAO.deleteById(id);
    }

    // Search by slug
    // This method will check if a slug already exists in the database.
    // The method should be created in the ICategoryDAO.
    @Override
    public boolean existsBySlug(String slug) {
        if (this.categoryDAO.existsBySlug(slug)) {
            return false; // If it exists, the process to create a new record will not be allowed to continue.
        }
        return true;
    }

    @Override
    public List<CategoryModel> getActiveCategories(Boolean isActive) {
        return this.categoryDAO.getActiveCategories(isActive);
    }

    /*
    // This is the correct implementation of the existsBySlug method
    @Override
    public boolean existsBySlug(String slug) {
        return this.categoryDAO.existsBySlug(slug);
    }
    */
}
