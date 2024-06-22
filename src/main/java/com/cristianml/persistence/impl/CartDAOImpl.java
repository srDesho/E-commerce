package com.cristianml.persistence.impl;

import com.cristianml.models.CartModel;
import com.cristianml.persistence.ICartDAO;
import com.cristianml.repository.CartRepository;
import com.cristianml.security.model.UserModel;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CartDAOImpl implements ICartDAO {

    // Inyects cartRepository
    private final CartRepository cartRepository;

    public CartDAOImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public CartModel save(CartModel cart) {
        this.cartRepository.save(cart);
        return cart;
    }

    @Override
    public Optional<CartModel> findCartByUser(UserModel currentUser) {
        return this.cartRepository.findCartModelByUser(currentUser);
    }


}
