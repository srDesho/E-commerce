package com.cristianml.repository;

import com.cristianml.models.CartItemModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends CrudRepository<CartItemModel, Long> {
}
