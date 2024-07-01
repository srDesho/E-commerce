package com.cristianml.persistence.impl;

import com.cristianml.dataProvider.ProductDataProvider;
import com.cristianml.models.CategoryModel;
import com.cristianml.models.ProductModel;
import com.cristianml.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductDAOImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductDAOImpl productDAO;

    // listProducts method
    @Test
    public void testListProducts() {
        when(this.productRepository.findAll()).thenReturn(ProductDataProvider.productListMock());
        List<ProductModel> result = this.productDAO.listProducts();

        assertNotNull(true);
        assertEquals(1, result.get(0).getId());
        assertEquals("Mouse Razer", result.get(0).getName());
        assertEquals("mouse-razer", result.get(0).getSlug());
        assertEquals("electronic product.", result.get(0).getDescription());
        assertEquals(BigDecimal.valueOf(500), result.get(0).getPrice());
        assertEquals("product1.png", result.get(0).getImage());
    }

    // findById method
    @Test
    public void testFindById() {
        Integer id = 1;

        when(this.productRepository.findById(anyInt())).thenReturn(ProductDataProvider.productMock());
        ProductModel result = this.productDAO.findById(id).get();

        assertNotNull(true);
        assertEquals(1, result.getId());
        assertEquals("Mouse Razer", result.getName());
        assertEquals("mouse-razer", result.getSlug());
        assertEquals("electronic product.", result.getDescription());
        assertEquals(BigDecimal.valueOf(500), result.getPrice());
        assertEquals("product1.png", result.getImage());
    }

    // save method
    @Test
    public void testSave() {
        ProductModel productModel = ProductDataProvider.productMock().get();

        this.productDAO.save(productModel);

        ArgumentCaptor<ProductModel> argumentCaptor = ArgumentCaptor.forClass(ProductModel.class);
        verify(this.productRepository).save(argumentCaptor.capture());

        assertEquals(1, argumentCaptor.getValue().getId());
        assertEquals("Mouse Razer", argumentCaptor.getValue().getName());
    }

    // deleteById method
    @Test
    public void testDeleteById() {
        Integer id = 1;

        this.productDAO.deleteById(id);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(this.productRepository).deleteById(anyInt());
        verify(this.productRepository).deleteById(argumentCaptor.capture());

        assertEquals(1, argumentCaptor.getValue());
    }

    // checkRelationWithCategory method
    @Test
    public void testCheckRelationWithCategory() {
        Integer idCategory = 1;
        CategoryModel categoryModel = CategoryModel.builder().id(1).build();

        when(this.productRepository.existsByCategory(any(CategoryModel.class))).thenReturn(true);
        boolean result = this.productDAO.checkRelationWithCategory(idCategory);

        assertTrue(result);
    }

    @Test
    public void testCheckRelationWithCategoryFalse() {
        Integer idCategory = 1;
        CategoryModel categoryModel = CategoryModel.builder().id(1).build();

        when(this.productRepository.existsByCategory(any(CategoryModel.class))).thenReturn(false);
        boolean result = this.productDAO.checkRelationWithCategory(idCategory);

        assertFalse(result);
    }

    // getAllActiveProducts method
    @Test
    public void testGetAllActiveProducts() {
        when(this.productRepository.findAllProductModelByIsActiveTrue()).thenReturn(ProductDataProvider.productListMock());
        List<ProductModel> result = this.productDAO.getAllActiveProducts();

        // Iterating with for normal
        for (int i = 0; i <= result.size() -1; i++) {
            assertTrue(result.get(i).getIsActive());
        }
    }

    // getActiveProductByCategory
    @Test
    public void testGetActiveProductByCategory() {
        String category = "electronic";

        when(this.productRepository.findAllActiveProductsByCategoryName(anyString()))
                .thenReturn(ProductDataProvider.productListActiveByCategoryNameMock());
        List<ProductModel> result = this.productDAO.getActiveProductsByCategory(category);

        // iterating with foreach
        for (ProductModel product : result) {
            assertTrue(product.getIsActive());
        }

    }
}
