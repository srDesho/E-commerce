package com.cristianml.controllers;

import com.cristianml.models.CartModel;
import com.cristianml.persistence.ICartDAO;
import com.cristianml.persistence.ICategoryDAO;
import com.cristianml.persistence.impl.CartDAOImpl;
import com.cristianml.service.impl.CartServiceImpl;
import com.cristianml.service.impl.OrderServiceImpl;
import com.cristianml.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ecommerce/customer/cart")
public class CartController {

    @Value("${cristian.values.base_url_upload}")
    private String baseUrlUpload;

    private final UserServiceImpl userService;
    private final CartServiceImpl cartService;
    private final OrderServiceImpl orderService;
    private final ICategoryDAO categoryService;

    public CartController(UserServiceImpl userService, CartServiceImpl cartService, OrderServiceImpl orderService,
                          ICategoryDAO categoryService) {
        this.userService = userService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.categoryService = categoryService;
    }


    // View cart
    @GetMapping("/view")
    public String viewCart(Model model) {
        CartModel cart = cartService.getCartByCurrentUser();
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", this.cartService.calculateTotalPrice(cart));

        return "/cart";
    }

    // addToCart
    @PostMapping("/add/{idProduct}/{quantity}")
    public String addToCard(@PathVariable("idProduct") Integer idProduct, @PathVariable("quantity") int quantity, Model model) {
        System.out.println("id: " + idProduct + " cantidad" + quantity);
        System.out.println("============================================" + this.userService.getCurrentUser().getUsername());
        this.cartService.addToCart(idProduct, quantity);

        return "redirect:/ecommerce/customer/cart/view";
    }

    // Update cart
    @PostMapping("/update/{itemId}")
    public String update(@PathVariable("itemId") Long itemId, @RequestParam(value = "operation") String operation) {
        this.cartService.updateCart(itemId, operation);
        return "redirect:/ecommerce/customer/cart/view";
    }

    // Generics
    @ModelAttribute
    public void setGenerics(Model model) {
        model.addAttribute("baseUrlUpload", this.baseUrlUpload);
        model.addAttribute("userAddress", this.userService.getCurrentUser().getAddress());
        model.addAttribute("categories", this.categoryService.getActiveCategories(true));
    }
}
