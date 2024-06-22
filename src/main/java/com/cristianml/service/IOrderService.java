package com.cristianml.service;

import com.cristianml.models.CartModel;
import com.cristianml.models.OrderModel;
import com.cristianml.security.model.UserModel;

import java.util.List;

public interface IOrderService {

    void save(OrderModel order);

    List<OrderModel> findOrdersByCurrentUser();
}
