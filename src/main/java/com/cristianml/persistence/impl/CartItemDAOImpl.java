package com.cristianml.persistence.impl;

import com.cristianml.models.CartItemModel;
import com.cristianml.persistence.ICartItemDAO;
import com.cristianml.repository.CartItemRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CartItemDAOImpl implements ICartItemDAO {

    private final CartItemRepository cartItemRepository;

    public CartItemDAOImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Optional<CartItemModel> findCartItemModelById(Long id) {
        return this.cartItemRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.cartItemRepository.deleteById(id);
    }

    @Override
    public void save(CartItemModel any) {
        this.cartItemRepository.save(any);
    }
}
