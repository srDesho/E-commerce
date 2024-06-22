package com.cristianml.persistence;

import com.cristianml.models.CartModel;
import com.cristianml.security.model.UserModel;

import java.util.Optional;

public interface ICartDAO {

    CartModel save(CartModel cart);

    Optional<CartModel> findCartByUser(UserModel currentUser);

}
