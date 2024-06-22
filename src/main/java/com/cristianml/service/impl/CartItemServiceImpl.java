package com.cristianml.service.impl;

import com.cristianml.models.CartItemModel;
import com.cristianml.persistence.ICartItemDAO;
import com.cristianml.service.ICartItemService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements ICartItemService {

    private final ICartItemDAO cartItemDAO;

    public CartItemServiceImpl(ICartItemDAO cartItemDAO) {
        this.cartItemDAO = cartItemDAO;
    }

    @Override
    public Optional<CartItemModel> findCartItemModelById(Long id) {
        return this.cartItemDAO.findCartItemModelById(id);
    }
}
