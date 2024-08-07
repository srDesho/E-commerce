package com.cristianml.service.impl;

import com.cristianml.models.CartItemModel;
import com.cristianml.models.CartModel;
import com.cristianml.models.ProductModel;
import com.cristianml.persistence.ICartDAO;
import com.cristianml.persistence.ICartItemDAO;
import com.cristianml.persistence.IProductDAO;
import com.cristianml.security.model.UserModel;
import com.cristianml.service.ICartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements ICartService {

    // Inyects cartRepository
    private final ICartDAO cartDAO;
    private final UserServiceImpl userService;
    private final IProductDAO productDAO;
    private final ICartItemDAO cartItemDAO;
    private final OrderServiceImpl orderService;


    public CartServiceImpl(ICartDAO cartDAO, UserServiceImpl userService, IProductDAO productDAO, ICartItemDAO cartItemDAO,
                           OrderServiceImpl orderService) {
        this.cartDAO = cartDAO;
        this.userService = userService;
        this.productDAO = productDAO;
        this.cartItemDAO = cartItemDAO;
        this.orderService = orderService;
    }

    @Override
    public void save(CartModel cart) {
        this.cartDAO.save(cart);
    }

    @Override
    public Optional<CartModel> findCartByUser(UserModel currentUser) {
        return this.cartDAO.findCartByUser(currentUser);
    }

    @Override
    public int countQuantityItems() {

        UserModel userModel = this.userService.getCurrentUser();
        // Create empty cart if the user doesn't have one.
        if (userModel.getCart() == null) {
            userModel.setCart(new CartModel());
        }

        return userModel
                .getCart()
                .getCartItems()
                .stream()
                .mapToInt(CartItemModel::getQuantity)
                .sum();
    }

    public CartModel getCartByCurrentUser() {
        UserModel currentUser = this.userService.getCurrentUser();
        return this.findCartByUser(currentUser)
                .orElseGet(() -> createCartUser(currentUser));
    }


    // Method to add a product to the shopping cart or update the quantity if it already exists in the cart.
    public void addToCart(Integer productId, int quantity) {
        // Retrieve the current user's shopping cart.
        CartModel cart = getCartByCurrentUser();

        // Find the product by its ID, throw an exception if the product is not found.
        ProductModel product = productDAO.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Try to find an existing cart item with the same product ID, or create a new cart item if not found.
        CartItemModel item = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseGet(() -> {
                    // Create new Item if not exists
                    CartItemModel newItem = new CartItemModel(cart, product, 0);
                    // Add to cat
                    cart.addCartItem(newItem);
                    return newItem;
                });

        // Update the quantity of the cart item
        item.setQuantity(item.getQuantity() + quantity);

        // Save the updated cart back to the database
        cartDAO.save(cart);
    }

    // Method to update the cart
    public void updateCart(Long itemId, String operation) {
        CartModel cart = getCartByCurrentUser();
        // Find the CartItemModel by itemId. If not found, throw a RuntimeException
        CartItemModel cartItem = cartItemDAO.findCartItemModelById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found."));

        int quantity = cartItem.getQuantity();
        if (operation.equals("sum")) {
            quantity += 1;
        } else {
            quantity -= 1;
        }

        // Update the quantity of the found CartItemModel
        cartItem.setQuantity(quantity);
        // Save the updated cart associated with the CartItemModel
        cartDAO.save(cartItem.getCart());

        if (cartItem.getQuantity() == 0) {
            this.cartItemDAO.deleteById(itemId);
        }
    }

    // Method to check out the buy
    public void checkout() {
        // Get the cart of the current user
        CartModel cart = getCartByCurrentUser();

        // Create an order from the current user's cart
        orderService.createOrderFromCart(cart);

        // Clear all items from the cart
        cart.getCartItems().clear();

        // Save the updated (empty) cart in the database
        cartDAO.save(cart);
    }

    // Create cart if user does not have.
    public CartModel createCartUser(UserModel currentUser) {

        CartModel userCart = new CartModel();
        userCart.setUser(currentUser);
        return cartDAO.save(userCart);
    }

    // Calculate total price
    public BigDecimal calculateTotalPrice(CartModel cart) {
        if (cart.getCartItems() != null) {
            BigDecimal totalAmount = cart.getCartItems().stream()
                    .map(item -> item.getProduct().getDiscountPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            return totalAmount;
        }
        return BigDecimal.valueOf(0);
    }

}
