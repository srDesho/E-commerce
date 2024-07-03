package com.cristianml.dataProvider;

import com.cristianml.models.CategoryModel;
import com.cristianml.models.ProductModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductDataProvider {

    public static List<ProductModel> productListMock() {
        return Arrays.asList(
                ProductModel.builder()
                        .id(1)
                        .name("Mouse Razer")
                        .slug("mouse-razer")
                        .description("electronic product.")
                        .price(BigDecimal.valueOf(500))
                        .image("product1.png")
                        .stock(10)
                        .discount(BigDecimal.valueOf(10))
                        .isActive(true)
                        .category(new CategoryModel())
                        .cartItem(new ArrayList<>())
                        .orderItem(new ArrayList<>())
                        .build(),
                ProductModel.builder()
                        .id(2)
                        .name("Keyboard Thetmaltake")
                        .slug("keyboard-thetmaltake")
                        .description("electronic product.")
                        .price(BigDecimal.valueOf(1000))
                        .image("product1.png")
                        .stock(10)
                        .discount(BigDecimal.valueOf(15))
                        .isActive(true)
                        .category(new CategoryModel())
                        .cartItem(new ArrayList<>())
                        .orderItem(new ArrayList<>())
                        .build(),
                ProductModel.builder()
                        .id(3)
                        .name("Laptop Asus")
                        .slug("laptop")
                        .description("electronic product.")
                        .price(BigDecimal.valueOf(70000))
                        .image("product3.png")
                        .stock(5)
                        .discount(BigDecimal.valueOf(0))
                        .isActive(true)
                        .category(new CategoryModel())
                        .cartItem(new ArrayList<>())
                        .orderItem(new ArrayList<>())
                        .build()
        );
    }

    public static Optional<ProductModel> productMock() {
        return Optional.of(
                ProductModel.builder()
                        .id(1)
                        .name("Mouse Razer")
                        .slug("mouse-razer")
                        .description("electronic product.")
                        .price(BigDecimal.valueOf(500))
                        .image("product1.png")
                        .stock(10)
                        .discount(BigDecimal.valueOf(10))
                        .discountPrice(BigDecimal.valueOf(490))
                        .isActive(true)
                        .category(new CategoryModel())
                        .cartItem(new ArrayList<>())
                        .orderItem(new ArrayList<>())
                        .build()
        );
    }

    public static List<ProductModel> productListActiveByCategoryNameMock() {
        return Arrays.asList(
                ProductModel.builder()
                        .id(1)
                        .name("Mouse Razer")
                        .slug("mouse-razer")
                        .description("electronic product.")
                        .price(BigDecimal.valueOf(500))
                        .image("product1.png")
                        .stock(10)
                        .discount(BigDecimal.valueOf(10))
                        .isActive(true)
                        .category(CategoryModel.builder()
                                .id(1)
                                .name("electronic")
                                .build())                        .cartItem(new ArrayList<>())
                        .orderItem(new ArrayList<>())
                        .build(),
                ProductModel.builder()
                        .id(2)
                        .name("Keyboard Thetmaltake")
                        .slug("keyboard-thetmaltake")
                        .description("electronic product.")
                        .price(BigDecimal.valueOf(1000))
                        .image("product1.png")
                        .stock(10)
                        .discount(BigDecimal.valueOf(15))
                        .isActive(true)
                        .category(CategoryModel.builder()
                                .id(1)
                                .name("electronic")
                                .build())                        .cartItem(new ArrayList<>())
                        .orderItem(new ArrayList<>())
                        .build(),
                ProductModel.builder()
                        .id(3)
                        .name("Laptop Asus")
                        .slug("laptop")
                        .description("electronic product.")
                        .price(BigDecimal.valueOf(70000))
                        .image("product3.png")
                        .stock(5)
                        .discount(BigDecimal.valueOf(0))
                        .isActive(true)
                        .category(CategoryModel.builder()
                                .id(1)
                                .name("electronic")
                                .build())
                        .cartItem(new ArrayList<>())
                        .orderItem(new ArrayList<>())
                        .build()
        );
    }
}
