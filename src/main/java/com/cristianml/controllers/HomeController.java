package com.cristianml.controllers;

import com.cristianml.models.ProductModel;
import com.cristianml.service.ICategoryService;
import com.cristianml.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/ecommerce")
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
    @GetMapping("/login")
    public String login() {
        return "/login";
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
        model.addAttribute("baseUrlUpload", this.baseUrlUpload);
    }
}
