package com.cristianml.persistence;

import com.cristianml.models.CartItemModel;

import java.util.Optional;

public interface ICartItemDAO {

    Optional<CartItemModel> findCartItemModelById(Long id);

    void deleteById(Long id);
}
