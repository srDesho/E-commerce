package com.cristianml.persistence.impl;

import com.cristianml.dataProvider.OrderDataProvider;
import com.cristianml.dataProvider.UserDataProvider;
import com.cristianml.models.OrderModel;
import com.cristianml.repository.OrderRepository;
import com.cristianml.security.model.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderDAOImplTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderDAOImpl orderDAO;

    // save method
    @Test
    public void testSave() {
        // Create a mock OrderModel
        OrderModel order = OrderDataProvider.optionalOrderMock().get();

        // Call the save method of the OrderDAO
        this.orderDAO.save(order);

        // Capture the OrderModel passed to the save method of the repository
        ArgumentCaptor<OrderModel> orderArgumentCaptor = ArgumentCaptor.forClass(OrderModel.class);
        verify(this.orderRepository).save(orderArgumentCaptor.capture());

        // Verify that the ID and user ID are correct
        OrderModel savedOrder = orderArgumentCaptor.getValue();
        assertEquals(1L, savedOrder.getId());
        assertEquals(1L, savedOrder.getUser().getId());
    }

    // findOrdersByCurrentUser method
    @Test
    public void testFindOrdersByCurrentUser() {
        // Create a mock list of OrderModel
        List<OrderModel> orderList = OrderDataProvider.listOrderMocks();
        UserModel user = UserDataProvider.optionalUserMock().get();

        // Mock the findAllOrderModelByUser method of the repository to return the mock list
        when(this.orderRepository.findAllOrderModelByUser(any(UserModel.class))).thenReturn(orderList);

        // Call the findOrdersByCurrentUser method of the OrderDAO
        List<OrderModel> result = this.orderDAO.findOrdersByCurrentUser(user);

        // Verify that each order in the returned list has the correct user ID
        for (OrderModel order : result) {
            assertEquals(1L, order.getUser().getId());
        }
    }

    @Test
    public void testFindAll() {
        // Given
        List<OrderModel> orderModelList = OrderDataProvider.listOrderMocks();

        // When
        when(this.orderRepository.findAll()).thenReturn(orderModelList);
        List<OrderModel> result = this.orderDAO.findAll();

        // Then
        for (OrderModel order : result) {
            assertEquals(1L, order.getUser().getId());
        }
    }
}
