package com.cristianml.controllers;

import com.cristianml.controllers.dto.UserDTO;
import com.cristianml.models.ProductModel;
import com.cristianml.service.ICategoryService;
import com.cristianml.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/ecommerce/customer")
public class HomeController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @Value("${cristian.values.base_url_upload}")
    private String baseUrlUpload;

    @GetMapping("/home")
    public String home() {
        return "/index";
    }

    // LOGIN
    @GetMapping("/loginRedirect")
    public String login(@RequestParam(value = "type", required = false) String type) {
        if (type.equalsIgnoreCase("admin")) {
            return "/admin/admin_login";
        }
        return "/login";
    }

    // LOGIN
    @GetMapping("/login")
    public String userLogin(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            RedirectAttributes flash,
                            Principal principal,
                            Model model) {

        // Check if there is a logged-in user
        if (principal != null) {
            flash.addFlashAttribute("clas", "success");
            flash.addFlashAttribute("message", "You have already logged in before.");
            return "redirect:/";
        }

        // Handle logout
        if (logout != null) {
            flash.addFlashAttribute("clas", "success");
            flash.addFlashAttribute("message", "You have successfully logged out.");
            return "redirect:/ecommerce/customer/login";
        }

        // If an error is received, display an error message
        if (error != null) {
            flash.addFlashAttribute("clas", "danger");
            flash.addFlashAttribute("message", "The entered data is incorrect, please try again.");
            return "redirect:/ecommerce/customer/login";
        }

        return "login"; // Render the normal user login view
    }
    // REGISTER
    @GetMapping("/register")
    public String register(UserDTO userDTO, Model model) {
        model.addAttribute("user", userDTO);
        return "/register";
    }

    // SEE PRODUCT DETAILS
    @GetMapping("/product/{id}")
    public String viewProductDetails(@PathVariable("id") Integer id, Model model) {
        Optional<ProductModel> optionalProduct = this.productService.findById(id);
        if (optionalProduct.isPresent()) {
           ProductModel product = optionalProduct.get();
           model.addAttribute("product", product);
        }

        return "/view_product";
    }

    // VIEW PRODUCTS
    @GetMapping("/products")
    public String product(@RequestParam(value = "category", defaultValue = "") String category, Model model) {
        model.addAttribute("categories", this.categoryService.getActiveCategories(true));
        model.addAttribute("products", this.productService.getActiveProductsByCategory(category));
        model.addAttribute("paramValue", category);
        return "/products";
    }

    // GENERICS
    @ModelAttribute
    public void setGenerics(Model model){
        model.addAttribute("categories", this.categoryService.getActiveCategories(true));
        model.addAttribute("baseUrlUpload", this.baseUrlUpload);
    }
}
