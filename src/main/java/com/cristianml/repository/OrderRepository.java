package com.cristianml.repository;

import com.cristianml.models.OrderModel;
import com.cristianml.security.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderModel, Long> {

    List<OrderModel> findAllOrderModelByUser(UserModel currentUser);

}
