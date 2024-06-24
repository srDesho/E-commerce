package com.cristianml.controllers;

import com.cristianml.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ecommerce/customer/order")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    // Method to view orders
    @GetMapping("/view")
    public String viewOrders(Model model) {
        model.addAttribute("orders", this.orderService.findOrdersByCurrentUser());
        return "view_orders";
    }
}
