package com.cristianml.persistence;

import com.cristianml.models.OrderModel;
import com.cristianml.security.model.UserModel;

import java.util.List;

public interface IOrderDAO {

    void save(OrderModel order);

    List<OrderModel> findOrdersByCurrentUser(UserModel currentUser);

}
