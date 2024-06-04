package com.cristianml.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ecommerce/admin")
public class AdminController {

    @GetMapping("/home")
    public String home() {
        return "/admin/index";
    }

}
