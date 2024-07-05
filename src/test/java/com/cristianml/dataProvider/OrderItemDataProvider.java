package com.cristianml.dataProvider;

import com.cristianml.models.OrderItemModel;
import com.cristianml.models.OrderModel;
import com.cristianml.models.ProductModel;

import java.math.BigDecimal;
import java.util.Optional;

public class OrderItemDataProvider {

    public static Optional<OrderItemModel> optionalOrderItemMock() {
        ProductModel product = ProductDataProvider.productMock().get();

        OrderItemModel orderItem = OrderItemModel.builder()
                .id(1L)
                .quantity(2)
                .price(new BigDecimal("50.00"))
                .product(product)
                .build();

        return Optional.of(orderItem);
    }
}
