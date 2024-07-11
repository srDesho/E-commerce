package com.cristianml.controllers;

import com.cristianml.controllers.dto.CategoryDTO;
import com.cristianml.controllers.dto.ProductDTO;
import com.cristianml.models.CategoryModel;
import com.cristianml.models.OrderModel;
import com.cristianml.models.ProductModel;
import com.cristianml.security.model.UserModel;
import com.cristianml.service.ICategoryService;
import com.cristianml.service.IOrderService;
import com.cristianml.service.IProductService;
import com.cristianml.service.IUserService;
import com.cristianml.utilities.Utilities;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.File;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/ecommerce/admin")
public class AdminController {

    @Value("${cristian.values.path_upload}")
    private String path_upload; // This name must be same name of path variable from Configuration class

    @Value("${cristian.values.base_url_upload}")
    private String baseUrlUpload;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @GetMapping("/home")
    public String home() {
        return "/admin/index";
    }

    @GetMapping("/login")
    public String adminLogin(@RequestParam(value = "error", required = false) String error,
                             @RequestParam(value = "logout", required = false) String logout,
                             RedirectAttributes flash,
                             Principal principal,
                             Model model) {

        // Check if there is a logged-in user
        if (principal != null) {
            flash.addFlashAttribute("clas", "success");
            flash.addFlashAttribute("message", "You have already logged in before.");
            return "redirect:/ecommerce/admin/home";
        }

        // Handle logout
        if (logout != null) {
            flash.addFlashAttribute("clas", "success");
            flash.addFlashAttribute("message", "You have successfully logged out.");
            return "redirect:/ecommerce/admin/login";
        }

        // If an error is received, display an error message
        if (error != null) {
            flash.addFlashAttribute("clas", "danger");
            flash.addFlashAttribute("message", "The entered data is incorrect, please try again.");
            return "redirect:/ecommerce/admin/login";
        }

        return "/admin/admin_login"; // Render the admin login view
    }

    // ========================================== CATEGORIES =======================================
    // VIEW CATEGORIES
    @GetMapping("/category/view")
    public String viewCategories() {
        return "/admin/view_categories";
    }

    // ADD CATEGORY
    @GetMapping("/category/add")
    public String addCategory(CategoryDTO categoryDTO, Model model){
        model.addAttribute("category", categoryDTO);
        return "/admin/add_category";
    }

    @PostMapping("/category/add")
    public String addCategoryPost(@Valid CategoryDTO categoryDTO, BindingResult result, @RequestParam("imageFile") MultipartFile file
        , RedirectAttributes flash, Model model) {

        // Validate data
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors()
                    .forEach( err -> {
                        errors.put(err.getField(),
                                "The field ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                    });

            model.addAttribute("errors", errors);
            model.addAttribute("category", categoryDTO);
            return "/admin/add_category";
        }

        // Validate image file
        if (file.isEmpty()) {
            flash.addFlashAttribute("clas", "danger");
            flash.addFlashAttribute("message", "The file for the image is mandatory, it must be JPG|JPEG|PNG");
            model.addAttribute("category", categoryDTO);
            return "redirect:/ecommerce/admin/category/add";
        }

        if (!file.isEmpty()) {
            String imageName = Utilities.saveFile(file, this.path_upload+"producto/");

            // Validate mime type
            if (imageName == "no") {
                flash.addFlashAttribute("clas", "danger");
                flash.addFlashAttribute("message", "The image file is not valid, it must be JPG|JPEG|PNG");
                model.addAttribute("category", categoryDTO);
                return "redirect:/ecommerce/admin/category/add";
            }

            if (imageName != null) {
                categoryDTO.setImage(imageName);
            }
        }

        // Generate slug
        String slug = Utilities.getSlug(categoryDTO.getName());

        // Check duplicate name

        if (this.categoryService.existsBySlug(slug)) {
            flash.addFlashAttribute("clas", "danger");
            flash.addFlashAttribute("message", "This category name already exists in the database");
            model.addAttribute("category", categoryDTO);
            return "redirect:/ecommerce/admin/category/add";
        }

        // Convert DTO to Entity
        CategoryModel category = new CategoryModel();
        category.setName(categoryDTO.getName());
        category.setSlug(slug);
        category.setImage(categoryDTO.getImage());
        category.setIsActive(true);
        this.categoryService.save(category);

        flash.addFlashAttribute("clas", "success");
        flash.addFlashAttribute("message", "Category created successfully.");
        model.addAttribute("category", categoryDTO);
        return "redirect:/ecommerce/admin/category/add";
    }

    // EDIT CATEGORY
    @GetMapping("/category/edit/{id}")
    public String editCategory(@PathVariable("id") Integer id, Model model){
        Optional<CategoryModel> optionalCategory = this.categoryService.findById(id);
        if (optionalCategory.isEmpty()) {
            System.out.println("NOT FOUND IN DB");
        }
        model.addAttribute("category", optionalCategory.get());
        return "/admin/edit_category";
    }

    @PostMapping("/category/edit/{id}")
    public String editCategoryPost(@Valid CategoryDTO categoryDTO, BindingResult result, @RequestParam("imageFile") MultipartFile file
            , RedirectAttributes flash, @PathVariable("id") Integer id, Model model) {

        // Validate data
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors()
                    .forEach( err -> {
                        errors.put(err.getField(),
                                "The field ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                    });

            model.addAttribute("errors", errors);
            model.addAttribute("category", categoryDTO);
            return "/admin/edit_category";
        }

        if (!file.isEmpty()) {
            String imageName = Utilities.saveFile(file, this.path_upload+"producto/");

            // Validate mime type
            if (imageName == "no") {
                flash.addFlashAttribute("clas", "danger");
                flash.addFlashAttribute("message", "The image file is not valid, it must be JPG|JPEG|PNG");
                model.addAttribute("category", categoryDTO);
                return "redirect:/ecommerce/admin/category/edit/" + id;
            }

            if (imageName != null) {
                categoryDTO.setImage(imageName);
            }
        }

        // Generate slug
        String slug = Utilities.getSlug(categoryDTO.getName());

        // Convert DTO to Entity
        Optional<CategoryModel> optionalCategory = this.categoryService.findById(id);
        if (optionalCategory.isEmpty()) {
            System.out.println("NOT FOUND IN DB");
        }
        CategoryModel category = optionalCategory.get();
        category.setName(categoryDTO.getName());
        category.setSlug(slug);
        category.setImage(categoryDTO.getImage());
        this.categoryService.save(category);

        flash.addFlashAttribute("clas", "success");
        flash.addFlashAttribute("message", "Category created successfully.");
        model.addAttribute("category", categoryDTO);
        return "redirect:/ecommerce/admin/category/edit/" + id;
    }

    // DELETE CATEGORIE

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id, RedirectAttributes flash) {
        Optional<CategoryModel> optionalCategory = this.categoryService.findById(id);
        if (optionalCategory.isPresent()) {
            CategoryModel categoryModel = optionalCategory.get();

            // Check if Category have relation with prdoucts
            if (this.productService.checkRelationWithCategory(id)) {
                flash.addFlashAttribute("clas", "danger");
                flash.addFlashAttribute("message", "This category could not be removed because it has related products.");
                return "redirect:/ecommerce/admin/category/view";
            }

            // Get the image
            File objImage = new File(this.path_upload+"producto/"+categoryModel.getImage());

            // Delete the image
            if (objImage.delete()) {

                // Check if Category have relation with prdoucts

                this.categoryService.deleteById(id);
                flash.addFlashAttribute("clas", "success");
                flash.addFlashAttribute("message", "This category deleted successfully.");
            } else {
                flash.addFlashAttribute("clas", "danger");
                flash.addFlashAttribute("message", "This category could not be removed, try again later.");
            }
        } else {
            flash.addFlashAttribute("clas", "danger");
            flash.addFlashAttribute("message", "This category could not be removed, try again later.");
        }

        return "redirect:/ecommerce/admin/category/view"; // This line of code cannot have any spaces.
    }

    // ========================================== PRODUCTS =========================================
    // VIEW PRODUCTS
    @GetMapping("/product/view")
    public String viewProducts(Model model) {
        model.addAttribute("products", this.productService.listProducts());
        return "/admin/view_products";
    }

    // ADD PRODUCT
    @GetMapping("/product/add")
    public String addProduct(ProductDTO product, Model model) {
        model.addAttribute("product", product);
        return "/admin/add_product";
    }

    @PostMapping("/product/add")
    public String addProductPost(@Valid ProductDTO productDTO, BindingResult result, @RequestParam("imageFile") MultipartFile file
            , RedirectAttributes flash, Model model) {

        // Validate data
        if(result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors()
                    .forEach( err -> {
                        errors.put(err.getField(),
                                "The field ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                    });

            model.addAttribute("errors", errors);
            model.addAttribute("product", productDTO);
            return "/admin/add_product";
        }

        // Check if no image was uploaded in the field from the view
        if (file.isEmpty()) {
            flash.addFlashAttribute("clas", "danger");
            flash.addFlashAttribute("message", "The file for the image is mandatory, it must be JPG|JPEG|PNG");
            model.addAttribute("product", productDTO);
            return "redirect:/ecommerce/admin/product/add";
        }

        // If an image was uploaded, create the image and verify the mime type
        if (!file.isEmpty()) {
            String imageName = Utilities.saveFile(file, this.path_upload+"producto/");

            // Check the value of imageName
            if (imageName == "no") {
                flash.addFlashAttribute("clas", "danger");
                flash.addFlashAttribute("message", "The image file is not valid, it must be JPG|JPEG|PNG");
                model.addAttribute("product", productDTO);
                return "redirect:/ecommerce/admin/product/add";
            }

            if (imageName != null) {
                productDTO.setImage(imageName);
            }
        }

        // Create the slug
        String slug = Utilities.getSlug(productDTO.getName());

        // Convert to Entity Product
        ProductModel product = new ProductModel();
        product.setName(productDTO.getName());
        product.setSlug(slug);
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImage(productDTO.getImage());
        product.setStock(productDTO.getStock());
        product.setCategory(productDTO.getCategory());
        product.setIsActive(true);
        product.setDiscount(BigDecimal.valueOf(0));
        product.setDiscountPrice(product.getPrice());
        this.productService.save(product);

        flash.addFlashAttribute("clas", "success");
        flash.addFlashAttribute("message", "Product create successfully.");
        return "redirect:/ecommerce/admin/product/add";
    }

    // EDIT PRODUCT

    @GetMapping("/product/edit/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, Model model){
        Optional<ProductModel> optionalProduct = this.productService.findById(id);
        if (optionalProduct.isEmpty()) {
            System.out.println("NOT FOUND IN DB");
        }
        ProductModel product = optionalProduct.get();
        // product.setCategory(product.getCategory());
        model.addAttribute("product", product);
        return "/admin/edit_product";
    }

    @PostMapping("/product/edit/{id}")
    public String deleteProductPost(@Valid ProductDTO productDTO, BindingResult result, @RequestParam("imageFile") MultipartFile file
        , RedirectAttributes flash, Model model, @PathVariable("id") Integer id) {

        // Validate data
        if(result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors()
                    .forEach( err -> {
                        errors.put(err.getField(),
                                "The field ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                    });

            model.addAttribute("errors", errors);
            model.addAttribute("product", productDTO);
            return "/admin/edit_product";
        }

        if (!file.isEmpty()) {
            String imageName = Utilities.saveFile(file, this.path_upload+"producto/");

            if (imageName == "no") {
                flash.addFlashAttribute("clas", "danger");
                flash.addFlashAttribute("message", "The image file is not valid, it must be JPG|JPEG|PNG");
                model.addAttribute("product", productDTO);
                return "redirect:/ecommerce/admin/product/edit/" + id;
            }

            if (imageName != null) {
                productDTO.setImage(imageName);
            } else {
                flash.addFlashAttribute("clas", "danger");
                flash.addFlashAttribute("message", "The image file is NULL");
                model.addAttribute("product", productDTO);
                return "redirect:/ecommerce/admin/product/edit/" + id;
            }
        }

        Optional<ProductModel> optionalProduct = this.productService.findById(id);
        if (optionalProduct.isEmpty()) {
            System.out.println("NOT FOUND IN DB");
        }

        // Convert DTO to Product entity
        ProductModel productModel = optionalProduct.get();
        productModel.setName(productDTO.getName());
        productModel.setSlug(Utilities.getSlug(productDTO.getName()));
        productModel.setDescription(productDTO.getDescription());
        productModel.setPrice(productDTO.getPrice());
        productModel.setImage(productDTO.getImage());
        productModel.setStock(productDTO.getStock());
        productModel.setCategory(productDTO.getCategory());

        // Discount
        productModel.setDiscount(productDTO.getDiscount());

        BigDecimal discountPrice = (BigDecimal) this.productService.calculateDiscountPrice(productDTO.getPrice()
            , productDTO.getDiscount());
        productModel.setDiscountPrice(discountPrice);

        this.productService.save(productModel);

        flash.addFlashAttribute("clas", "success");
        flash.addFlashAttribute("message", "Product update successfully.");
        return "redirect:/ecommerce/admin/product/edit/" + id;

    }

    // DELETE PRODUCT

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, RedirectAttributes flash) {

        Optional<ProductModel> optionalProduct = this.productService.findById(id);
        if (optionalProduct.isPresent()) {
            ProductModel productModel = optionalProduct.get();
            // Get the image
            File objImage = new File(this.path_upload+"producto/"+productModel.getImage());

            // Delete the image
            if (objImage.delete()) {
                this.productService.deleteById(id);
                flash.addFlashAttribute("clas", "success");
                flash.addFlashAttribute("message", "Product  deleted succesfully.");
            } else {
                flash.addFlashAttribute("clas", "danger");
                flash.addFlashAttribute("message", "Product could not be removed try again later");
            }
        } else {
            flash.addFlashAttribute("clas", "danger");
            flash.addFlashAttribute("message", "Product could not be removed try again later");
        }

        return "redirect:/ecommerce/admin/product/view"; // This line of code cannot have any spaces.
    }

    // =============================== View Orders ==============================
    @GetMapping("/view-orders")
    public String viewOrders(Model model) {
        List<OrderModel> orderList = this.orderService.findAll();
        model.addAttribute("orders", orderList);
        return "/admin/view_orders";
    }

    // =============================== View Users =================================
    @GetMapping("/user-details")
    public String userDetails(Model model) {
        List<UserModel> users = this.userService.findAll();
        model.addAttribute("users", users);
        return "/admin/user_details";
    }

    // GENERICS
    @ModelAttribute
    public void setGenerics(Model model) {
        model.addAttribute("categories", this.categoryService.listCategories());
        model.addAttribute("baseUrlUpload", this.baseUrlUpload);
    }
}
