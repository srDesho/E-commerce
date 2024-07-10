package com.cristianml.persistence.impl;

import com.cristianml.models.OrderModel;
import com.cristianml.persistence.IOrderDAO;
import com.cristianml.repository.OrderRepository;
import com.cristianml.security.model.UserModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDAOImpl implements IOrderDAO {

    private final OrderRepository orderRepository;

    public OrderDAOImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void save(OrderModel order) {
        this.orderRepository.save(order);
    }

    @Override
    public List<OrderModel> findAll() {
        return (List<OrderModel>) this.orderRepository.findAll();
    }

    @Override
    public List<OrderModel> findOrdersByCurrentUser(UserModel currentUser) {
        return this.orderRepository.findAllOrderModelByUser(currentUser);
    }
}
