package com.cristianml.service;

import com.cristianml.models.CartModel;
import com.cristianml.security.model.UserModel;

import java.util.Optional;

public interface ICartService {

    void save(CartModel cart);

    Optional<CartModel> findCartByUser(UserModel currentUser);
    int countQuantityItems();
}
