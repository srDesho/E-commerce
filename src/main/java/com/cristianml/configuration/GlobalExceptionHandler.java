package com.cristianml.configuration;

import com.cristianml.service.impl.UserServiceImpl;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserServiceImpl.UnauthenticatedUserException.class)
    public String handleUnauthenticatedUserException(UserServiceImpl.UnauthenticatedUserException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Please log in to access this page.");
        return "redirect:/ecommerce/customer/login"; // Redirect to login page if user is not authenticated
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, RedirectAttributes redirectAttributes, Model model) {
        if (ex.getMessage().contains("User not authenticated") || ex.getMessage().contains("User not found")) {
            redirectAttributes.addFlashAttribute("error", "Please log in to access this page.");
            return "redirect:/ecommerce/customer/login"; // Redirect to login page if user is not authenticated or not found
        }
        // Handle other exceptions if necessary
        model.addAttribute("error", ex.getMessage());
        return "error"; // Return error page for other exceptions
    }
}
