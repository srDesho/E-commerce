package com.cristianml.repository;

import com.cristianml.models.OrderItemModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItemModel, Long> {
}
