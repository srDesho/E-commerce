package com.cristianml.service.impl;

import com.cristianml.controllers.dto.ProductDTO;
import com.cristianml.models.CategoryModel;
import com.cristianml.models.ProductModel;
import com.cristianml.persistence.IProductDAO;
import com.cristianml.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class ProductServiceImpl implements IProductService {

    private final IProductDAO productDAO;

    @Autowired
    public ProductServiceImpl(IProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public List<ProductModel> listProducts() {
        return this.productDAO.listProducts();
    }

    @Override
    public Optional<ProductModel> findById(Integer id) {
        return this.productDAO.findById(id);
    }

    @Override
    public void save(ProductModel product) {
        this.productDAO.save(product);
    }

    @Override
    public void deleteById(Integer id) {
        this.productDAO.deleteById(id);
    }

    @Override
    public boolean checkRelationWithCategory(Integer idCategory) {
        if (this.productDAO.checkRelationWithCategory(idCategory)) {
            return true;
        }
        return false;
    }

    @Override
    public BigDecimal calculateDiscountPrice(BigDecimal price, BigDecimal discount) {
        BigDecimal discountAmount = price.multiply(discount).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
        BigDecimal discountPrice = price.subtract(discountAmount);
        return discountPrice;
    }

    @Override
    public List<ProductModel> getAllActiveProducts() {
        return this.productDAO.getAllActiveProducts();
    }

    @Override
    public List<ProductModel> getActiveProductsByCategory(String category) {
        if (category.isBlank()) {
            return this.productDAO.getAllActiveProducts();
        }
        return this.productDAO.getActiveProductsByCategory(category);
    }


}
