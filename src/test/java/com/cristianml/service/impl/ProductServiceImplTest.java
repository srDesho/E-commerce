package com.cristianml.service.impl;

import com.cristianml.dataProvider.ProductDataProvider;
import com.cristianml.models.ProductModel;
import com.cristianml.persistence.impl.ProductDAOImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    ProductDAOImpl productDAO;

    @InjectMocks
    ProductServiceImpl productService;

    // listProducts method
    @Test
    public void testListProducts() {
        when(this.productDAO.listProducts()).thenReturn(ProductDataProvider.productListMock());
        List<ProductModel> result = this.productService.listProducts();

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

        when(this.productDAO.findById(anyInt())).thenReturn(ProductDataProvider.productMock());
        ProductModel result = this.productService.findById(id).get();

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

        this.productService.save(productModel);

        ArgumentCaptor<ProductModel> argumentCaptor = ArgumentCaptor.forClass(ProductModel.class);
        verify(this.productDAO).save(argumentCaptor.capture());

        assertEquals(1, argumentCaptor.getValue().getId());
        assertEquals("Mouse Razer", argumentCaptor.getValue().getName());
    }

    // deleteById method
    @Test
    public void testDeleteById() {
        Integer id = 1;

        this.productService.deleteById(id);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(this.productDAO).deleteById(anyInt());
        verify(this.productDAO).deleteById(argumentCaptor.capture());

        assertEquals(1, argumentCaptor.getValue());
    }

    // checkRelationWithCategory method
    @Test
    public void testCheckRelationWithCategory() {
        Integer idCategory = 1;

        when(this.productDAO.checkRelationWithCategory(anyInt())).thenReturn(true);
        boolean result = this.productService.checkRelationWithCategory(idCategory);

        assertTrue(result);
    }

    @Test
    public void testCheckRelationWithCategoryFalse() {
        Integer idCategory = 1;

        when(this.productDAO.checkRelationWithCategory(anyInt())).thenReturn(false);
        boolean result = this.productService.checkRelationWithCategory(idCategory);

        assertFalse(result);
    }

    // getAllActiveProducts method
    @Test
    public void testGetAllActiveProducts() {
        when(this.productDAO.getAllActiveProducts()).thenReturn(ProductDataProvider.productListMock());
        List<ProductModel> result = this.productService.getAllActiveProducts();

        // Iterating with for normal
        for (int i = 0; i <= result.size() -1; i++) {
            assertTrue(result.get(i).getIsActive());
        }
    }

    // getActiveProductByCategory
    @Test
    public void testGetActiveProductByCategory_NotBlankCategory() {
        String category = "electronic";

        when(this.productDAO.getActiveProductsByCategory(anyString()))
                .thenReturn(ProductDataProvider.productListActiveByCategoryNameMock());
        List<ProductModel> result = this.productService.getActiveProductsByCategory(category);

        // iterating with foreach
        for (ProductModel product : result) {
            assertTrue(product.getIsActive());
        }
        // We verify that the getActiveProductsByCategory method was called once with the given
        // category and that the getAllActiveProducts method was not called.
        verify(productDAO, times(1)).getActiveProductsByCategory(category);
        verify(productDAO, times(0)).getAllActiveProducts();
    }

    // getActiveProductByCategory
    @Test
    public void testGetActiveProductByCategory_BlankCategory() {
        String category = "";

        when(this.productDAO.getAllActiveProducts())
                .thenReturn(ProductDataProvider.productListActiveByCategoryNameMock());
        List<ProductModel> result = this.productService.getActiveProductsByCategory(category);

        // iterating with foreach
        for (ProductModel product : result) {
            assertTrue(product.getIsActive());
        }
        // We verify that the getAllActiveProducts method was called once and that the
        // getActiveProductsByCategory method was not called.
        verify(productDAO, times(1)).getAllActiveProducts();
        verify(productDAO, times(0)).getActiveProductsByCategory(category);
    }

    // calculateDiscountPrice method
    @Test
    public void testCalculateDiscountPrice() {
        BigDecimal price = BigDecimal.valueOf(100);
        BigDecimal discount = BigDecimal.valueOf(10);

        BigDecimal result = this.productService.calculateDiscountPrice(price, discount);

        assertEquals(BigDecimal.valueOf(90), result);
    }
}
