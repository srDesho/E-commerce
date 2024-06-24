package com.cristianml.service;

import com.cristianml.models.CartItemModel;

import java.util.Optional;

public interface ICartItemService {

    Optional<CartItemModel> findCartItemModelById(Long id);

    void deleteById(Long id);

}
