package com.cristianml.controllers;

import com.cristianml.controllers.dto.ProductDTO;
import com.cristianml.models.ProductModel;
import com.cristianml.service.ICategoryService;
import com.cristianml.service.IProductService;
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


import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/ecommerce/admin")
public class AdminController {

    @Value("${cristian.values.path_upload}")
    private String path_upload; // This name must be same name of path variable from Configuration class

    @Value("cristian.values.base_url_upload")
    private String baseUrlUpload;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @GetMapping("/home")
    public String home() {
        return "/admin/index";
    }

    // Add Product
    @GetMapping("/product/add")
    public String addProduct(ProductDTO product, Model model) {
        model.addAttribute("product", product);
        return "/admin/add_product";
    }

    @PostMapping("/product/add")
    public String addProductPost(@Valid ProductDTO productDTO, BindingResult result, @RequestParam("imageFile") MultipartFile file
            , RedirectAttributes flash, Model model) {

        // Validate datas
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
            } else {
                flash.addFlashAttribute("clas", "danger");
                flash.addFlashAttribute("message", "The image file is NULL");
                model.addAttribute("product", productDTO);
                return "redirect:/ecommerce/admin/product/add";
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
        product.setDiscount(0);
        product.setDiscountPrice(product.getPrice());
        this.productService.save(product);

        flash.addFlashAttribute("clas", "success");
        flash.addFlashAttribute("message", "Product create successfully.");
        return "redirect:/ecommerce/admin/product/add";
    }


    // Generics
    @ModelAttribute
    public void setGenerics(Model model) {
        model.addAttribute("categories", this.categoryService.listCategories());
        model.addAttribute("baseUrlUpload", this.baseUrlUpload);
    }

}
