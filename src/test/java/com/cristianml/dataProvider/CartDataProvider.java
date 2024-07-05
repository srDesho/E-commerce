package com.cristianml.dataProvider;

import com.cristianml.models.CartItemModel;
import com.cristianml.models.CartModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class CartDataProvider {

    public static Optional<CartModel> optionalCartMock() {
        return Optional.of(
                CartModel.builder()
                .id(1L)
                .user(UserDataProvider.optionalUserMock().get())
                .cartItems(new ArrayList<>())
                .build()
        );
    }

    public static Optional<CartModel> optionalCartNullItemsMock() {
        return Optional.of(
                CartModel.builder()
                .id(1L)
                .user(UserDataProvider.optionalUserMock().get())
                .cartItems(null)
                .build()
        );
    }

    public static Optional<CartModel> optionalCartMockWithItems() {
        return Optional.of(
                CartModel.builder()
                        .id(1L)
                        .user(UserDataProvider.optionalUserMock().get())
                        .cartItems(Collections.singletonList(CartItemModel.builder()
                                .cart(CartModel.builder().build())
                                .product(ProductDataProvider.productMock().get())
                                .quantity(2)
                                .build()
                        ))
                        .build()
        );
    }



}
