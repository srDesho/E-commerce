package com.cristianml.configuration;

import com.cristianml.service.ICartService;
import com.cristianml.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * The CartInterceptor class is a Spring component that implements the HandlerInterceptor interface.
 * It is used to intercept HTTP requests and add common attributes to the ModelAndView object before
 * the view is rendered. This ensures that these attributes are available to all views in the application.
 */
@Component
public class CartInterceptor implements HandlerInterceptor {

    private final ICartService cartService;
    private final UserServiceImpl userServiceImpl;

    /**
     * Constructor for CartInterceptor.
     * It initializes the cartService and userServiceImpl dependencies.
     *
     * @param cartService      the service to manage cart operations
     * @param userServiceImpl  the service to manage user operations
     */
    public CartInterceptor(ICartService cartService, UserServiceImpl userServiceImpl) {
        this.cartService = cartService;
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * Pre-handle method is called before the actual handler is executed.
     * It returns true to let the request proceed.
     *
     * @param request   the HttpServletRequest object
     * @param response  the HttpServletResponse object
     * @param handler   the handler object
     * @return boolean  whether to continue with the request processing
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }

    /**
     * Post-handle method is called after the handler is executed but before the view is rendered.
     * It adds attributes to the ModelAndView object if the user is authenticated.
     *
     * @param request       the HttpServletRequest object
     * @param response      the HttpServletResponse object
     * @param handler       the handler object
     * @param modelAndView  the ModelAndView object
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (modelAndView != null && userServiceImpl.isAuthenticated()) {
            // Get the current user's ID and add it to the model
            Long currentUserId = userServiceImpl.getCurrentUser().getId();
            modelAndView.addObject("currentUserId", currentUserId);

            // Get the quantity of items in the current user's cart and add it to the model
            modelAndView.addObject("quantityItems", cartService.countQuantityItems());
        }
    }

    /**
     * After-completion method is called after the view is rendered.
     * It can be used for resource cleanup, but it is empty in this implementation.
     *
     * @param request   the HttpServletRequest object
     * @param response  the HttpServletResponse object
     * @param handler   the handler object
     * @param ex        the exception thrown, if any
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}


