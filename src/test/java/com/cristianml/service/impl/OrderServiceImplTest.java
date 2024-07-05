package com.cristianml.service.impl;

import com.cristianml.dataProvider.OrderDataProvider;
import com.cristianml.dataProvider.OrderItemDataProvider;
import com.cristianml.dataProvider.UserDataProvider;
import com.cristianml.models.CartItemModel;
import com.cristianml.models.CartModel;
import com.cristianml.models.OrderItemModel;
import com.cristianml.models.OrderModel;
import com.cristianml.persistence.IOrderDAO;
import com.cristianml.repository.OrderRepository;
import com.cristianml.security.model.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    IOrderDAO orderDAO;
    @Mock
    UserServiceImpl userService;
    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    // save method
    @Test
    public void testSave() {

        OrderModel order = OrderDataProvider.optionalOrderMock().get();

        this.orderService.save(order);

        ArgumentCaptor<OrderModel> argumentCaptor = ArgumentCaptor.forClass(OrderModel.class);
        verify(this.orderDAO).save(argumentCaptor.capture());

        assertEquals(1L, argumentCaptor.getValue().getId());
    }

    // Test method for findOrdersByCurrentUser()
    @Test
    public void testFindOrdersByCurrentUser() {
        // Mock the list of simulated orders
        List<OrderModel> orderList = OrderDataProvider.listOrderMocks();

        // Mock the current user
        UserModel user = UserDataProvider.optionalUserMock().get();
        when(this.userService.getCurrentUser()).thenReturn(user);

        // Mock the DAO method findOrdersByCurrentUser() to return the simulated list
        when(this.orderDAO.findOrdersByCurrentUser(any(UserModel.class))).thenReturn(orderList);

        // Call the method under test
        List<OrderModel> result = this.orderService.findOrdersByCurrentUser();

        // Verify that the returned list contains the same number of orders as the simulated list
        assertEquals(orderList.size(), result.size());

        // Verify that each order belongs to the current user
        for (OrderModel order : result) {
            assertEquals(user.getId(), order.getUser().getId());
        }
    }

    // Test method for createOrderFromCart()
    @Test
    public void testCreateOrderFromCart() {
        // Create a simulated shopping cart
        CartModel cart = new CartModel();
        UserModel user = UserDataProvider.optionalUserMock().get();
        cart.setUser(user);

        // Create a simulated cart item
        OrderItemModel orderItem = OrderItemDataProvider.optionalOrderItemMock().get();
        CartItemModel cartItem = new CartItemModel();
        cartItem.setProduct(orderItem.getProduct());
        cartItem.setQuantity(orderItem.getQuantity());
        cart.setCartItems(Collections.singletonList(cartItem));

        // Configure mock behavior for the save method of the order DAO
        doNothing().when(orderDAO).save(any(OrderModel.class));

        // Execute the method we want to test
        orderService.createOrderFromCart(cart);

        // Verify that the save method of the order DAO is called exactly once
        verify(orderDAO, times(1)).save(any(OrderModel.class));
    }
}
