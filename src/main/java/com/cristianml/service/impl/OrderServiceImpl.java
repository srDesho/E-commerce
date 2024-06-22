package com.cristianml.service.impl;

import com.cristianml.models.CartItemModel;
import com.cristianml.models.CartModel;
import com.cristianml.models.OrderItemModel;
import com.cristianml.models.OrderModel;
import com.cristianml.persistence.IOrderDAO;
import com.cristianml.repository.OrderRepository;
import com.cristianml.security.model.UserModel;
import com.cristianml.service.IOrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    private final IOrderDAO orderDAO;
    private final UserServiceImpl userService;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository, IOrderDAO orderDAO, UserServiceImpl userService, OrderRepository orderRepository1) {
        this.orderDAO = orderDAO;
        this.userService = userService;
        this.orderRepository = orderRepository1;
    }

    @Override
    public void save(OrderModel order) {
        this.orderDAO.save(order);
    }

    @Override
    public List<OrderModel> findOrdersByCurrentUser() {
        UserModel currentUser = this.userService.getCurrentUser();
        return this.orderDAO.findOrdersByCurrentUser(currentUser);
    }

    // Method for create an order from shopping cart
    public void createOrderFromCart(CartModel cart) {
        // Create a new order object (OrderModel)
        OrderModel order = new OrderModel();

        // Assign the user from the cart to the order
        order.setUser(cart.getUser());

        // Generate and assign a unique ID to the order
        order.setOrderId(generatedOrderId());

        // Set the current date and time as the order date
        order.setOrderDate(LocalDateTime.now());

        // Calculate the total amount of the order by summing up the total amounts of all cart items
        BigDecimal totalAmount = cart.getCartItems().stream()
                .map(item -> item.getProduct().getDiscountPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);

        // Set the payment method and order status to "Pending"
        order.setPaymentMethod("Pending");
        order.setStatus("Pending");

        // Iterate over the cart items to create order items (OrderItemModel) associated with the order
        for (CartItemModel cartItem : cart.getCartItems()) {
            OrderItemModel orderItem = new OrderItemModel();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getDiscountPrice());
            order.getOrderItems().add(orderItem);
        }

        // Save the order to the database through the corresponding DAO (orderDAO)
        this.orderDAO.save(order);
    }

    // Method for generated an order id(string type)
    private String generatedOrderId() {
        return "PROD-ORD-" + String.format("%05d", orderRepository.count() + 1);
    }
}
