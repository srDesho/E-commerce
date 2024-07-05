package com.cristianml.dataProvider;

import com.cristianml.models.OrderItemModel;
import com.cristianml.models.OrderModel;
import com.cristianml.security.model.UserModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrderDataProvider {

    public static Optional<OrderModel> optionalOrderMock() {
        UserModel user = UserDataProvider.optionalUserMock().get();

        OrderModel order = OrderModel.builder()
                .id(1L)
                .orderDate(LocalDateTime.now())
                .orderId("PROD-ORD-00001")
                .totalAmount(new BigDecimal("100.00"))
                .paymentMethod("Credit Card")
                .status("PENDING")
                .user(user)
                .orderItems(Collections.singletonList(
                        OrderItemModel.builder()
                                .id(1L)
                                .quantity(2)
                                .price(new BigDecimal("50.00"))
                                .product(ProductDataProvider.productMock().get())
                                .order(OrderModel.builder().build())
                                .build()
                ))
                .build();

        return Optional.of(order);
    }

    public static List<OrderModel> listOrderMocks() {
        List<OrderModel> orders = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            UserModel user = UserDataProvider.optionalUserMock().get();
            OrderItemModel orderItem = OrderItemDataProvider.optionalOrderItemMock().get();

            OrderModel order = OrderModel.builder()
                    .id((long) i)
                    .orderDate(LocalDateTime.now())
                    .orderId("ORDER12345" + i)
                    .totalAmount(new BigDecimal("100.00").multiply(BigDecimal.valueOf(i)))
                    .paymentMethod("Credit Card")
                    .status("PENDING")
                    .user(user)
                    .orderItems(Collections.singletonList(orderItem))
                    .build();

            orders.add(order);
        }

        return orders;
    }

}
