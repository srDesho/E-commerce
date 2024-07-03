package com.cristianml.dataProvider;

import com.cristianml.models.CartModel;
import com.cristianml.security.model.UserModel;

import java.util.ArrayList;
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



}
