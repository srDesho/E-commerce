package com.cristianml.controllers;

import com.cristianml.controllers.dto.UserDTO;
import com.cristianml.models.ProductModel;
import com.cristianml.security.model.RoleModel;
import com.cristianml.security.model.UserModel;
import com.cristianml.security.repository.RoleRepository;
import com.cristianml.service.ICategoryService;
import com.cristianml.service.IProductService;
import com.cristianml.service.IUserService;
import com.cristianml.service.converter.UserConverter;
import com.cristianml.service.impl.UserServiceImpl;
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

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ecommerce/customer")
public class HomeController {

    @Value("${cristian.values.path_upload}")
    private String path_upload; // This name must be same name of path variable from Configuration class

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @Value("${cristian.values.base_url_upload}")
    private String baseUrlUpload;

    @Autowired
    private IUserService userService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

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

    @PostMapping("/register")
    public String registerPost(@Valid UserDTO userDTO, BindingResult result, @RequestParam("image") MultipartFile file,
                           @RequestParam("confirm_password") String confirmPassword, RedirectAttributes flash, Model model) {

        // Validate the password
        if (!userDTO.getPassword().equals(confirmPassword)) {
            // add error message if they aren't equals.
            result.rejectValue("password", "error.user", "Password do not match");
        }

        // Validate data
        if(result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors()
                    .forEach( err -> {
                        errors.put(err.getField(),
                                "The field ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                    });

            model.addAttribute("errors", errors);
            model.addAttribute("user", userDTO);
            System.out.println("Validation errors: " + errors); // Debugging line
            return "/register";
        }

        System.out.println("validation complete");
        // Add default image if the file is empty
        String imageName = "default.png";
        if (file.isEmpty()) {
            userDTO.setProfileImage(imageName);
        }

        // If the user charge a profile image
        if (!file.isEmpty()) {
            imageName = Utilities.saveFile(file, this.path_upload.concat("ecommerce/profiles/"));

            // Check the value of imageName
            if (imageName == "no") {
                flash.addFlashAttribute("clas", "danger");
                flash.addFlashAttribute("message", "The image file is not valid, it must be JPG|JPEG|PNG");
                model.addAttribute("user", userDTO);
                return "redirect:/ecommerce/customer/register";
            }

            if (imageName != null) {
                userDTO.setProfileImage(imageName);
            }
        }

        // Validate if the username already exists in the database
        if (this.userService.existsByUsername(userDTO.getUsername())) {
            flash.addFlashAttribute("clas", "danger");
            flash.addFlashAttribute("message", "This username already exists in the database");
            model.addAttribute("user", userDTO);
            return "redirect:/ecommerce/customer/register";
        }

        // Convert userDTO to userModel
        UserModel userModel = this.userConverter.toModel(userDTO);
        userModel.setEnable(true);
        userModel.setAccountNoExpired(true);
        userModel.setAccountNoLocked(true);
        userModel.setCredentialNoExpired(true);

        // Get the role from database to assign to userModel
        Set<RoleModel> roleModelSet = this.roleRepository.findRoleEntitiesByRoleEnumIn(List.of("USER"))
                .stream().collect(Collectors.toSet());
        userModel.setRoleList(roleModelSet);

        // Save user in the database
        this.userService.save(userModel);

        flash.addFlashAttribute("clas", "success");
        flash.addFlashAttribute("message", "User added successfully.");
        return "redirect:/ecommerce/customer/register";
    }

    // View Profile

    @GetMapping("/view-profile/{id}")
    public String viewProfile(@PathVariable("id") Long id, RedirectAttributes flash, Model model) {
        Optional<UserModel> userOptional = this.userService.findById(id);
        if (userOptional.isEmpty()) {
            flash.addFlashAttribute("clas", "danger");
            flash.addFlashAttribute("message", "User not found.");
            return "redirect:/ecommerce/customer/view-profile/";
        }
        model.addAttribute("user", userOptional.get());
        return "/view-profile";
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

        // Get Id current User
        if (userServiceImpl.isAuthenticated()) {
            Long currentUserId = this.userServiceImpl.getCurrentUser().getId();
            model.addAttribute("currentUserId", currentUserId);
        }
    }
}
