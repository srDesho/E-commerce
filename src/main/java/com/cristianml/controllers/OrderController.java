package com.cristianml.controllers;

import com.cristianml.service.ICartService;
import com.cristianml.service.impl.OrderServiceImpl;
import com.cristianml.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ecommerce/customer/order")
public class OrderController {

    private final OrderServiceImpl orderService;
    private final UserServiceImpl userServiceImpl;
    private final ICartService cartService;

    public OrderController(OrderServiceImpl orderService, UserServiceImpl userServiceImpl, ICartService cartService) {
        this.orderService = orderService;
        this.userServiceImpl = userServiceImpl;
        this.cartService = cartService;
    }

    // Method to view orders
    @GetMapping("/view")
    public String viewOrders(Model model) {
        model.addAttribute("orders", this.orderService.findOrdersByCurrentUser());
        return "view_orders";
    }

    // Generics
    @ModelAttribute
    public void setGenerics(Model model) {
        // Get Id current User
        if (userServiceImpl.isAuthenticated()) {
            Long currentUserId = this.userServiceImpl.getCurrentUser().getId();
            model.addAttribute("currentUserId", currentUserId);
            model.addAttribute("quantityItems", this.cartService.countQuantityItems());
        }
    }
}
