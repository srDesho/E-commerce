package com.cristianml.persistence.impl;

import com.cristianml.models.CategoryModel;
import com.cristianml.models.ProductModel;
import com.cristianml.persistence.IProductDAO;
import com.cristianml.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component // We define this class as a bean so that it can be injected from another class.
public class ProductDAOImpl implements IProductDAO {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductModel> listProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Optional<ProductModel> findById(Integer id) {
        return this.productRepository.findById(id);
    }

    @Override
    public void save(ProductModel product) {
        this.productRepository.save(product);
    }

    @Override
    public void deleteById(Integer id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public boolean checkRelationWithCategory(Integer idCategory) {
        CategoryModel category = new CategoryModel();
        category.setId(idCategory);
        if (this.productRepository.existsByCategory(category)) {
            return true;
        }
        return false;
    }

    @Override
    public List<ProductModel> getAllActiveProducts() {
        return this.productRepository.findAllProductModelByIsActiveTrue();
    }

    @Override
    public List<ProductModel> getActiveProductsByCategory(String category) {
        return this.productRepository.findAllActiveProductsByCategoryName(category);
    }

}
