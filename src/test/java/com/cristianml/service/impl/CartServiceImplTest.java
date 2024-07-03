package com.cristianml.service.impl;

import com.cristianml.dataProvider.CartDataProvider;
import com.cristianml.dataProvider.ProductDataProvider;
import com.cristianml.dataProvider.UserDataProvider;
import com.cristianml.models.CartItemModel;
import com.cristianml.models.CartModel;
import com.cristianml.models.ProductModel;
import com.cristianml.persistence.ICartDAO;
import com.cristianml.persistence.ICartItemDAO;
import com.cristianml.persistence.IProductDAO;
import com.cristianml.security.model.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

    @Mock
    ICartDAO cartDAO;

    @Mock
    UserServiceImpl userService;

    @Mock
    IProductDAO productDAO;

    @Mock
    ICartItemDAO cartItemDAO;

    @Mock
    OrderServiceImpl orderService;

    @InjectMocks
    CartServiceImpl cartService;

    // save method
    @Test
    public void testSave() {
        CartModel cart = CartDataProvider.optionalCartMock().get();

        this.cartService.save(cart);

        ArgumentCaptor<CartModel> cartArgumentCaptor = ArgumentCaptor.forClass(CartModel.class);
        verify(this.cartDAO).save(cartArgumentCaptor.capture());
        assertEquals(1l, cartArgumentCaptor.getValue().getId());
        assertEquals(1L, cartArgumentCaptor.getValue().getUser().getId());
    }

    // findCartByUser method
    @Test
    public void testFindCartByUser() {
        UserModel user = UserDataProvider.optionalUserMock().get();

        when(this.cartDAO.findCartByUser(any(UserModel.class))).thenReturn(CartDataProvider.optionalCartMock());
        Optional<CartModel> result = this.cartService.findCartByUser(user);

        assertEquals(1L, result.get().getId());
        assertEquals(user.getId(), result.get().getUser().getId());
    }

    // getCartByCurrentUser method
    @Test
    public void testGetCartByCurrentUser_CartExists() {
        UserModel user = UserDataProvider.optionalUserMock().get();
        CartModel cart = CartDataProvider.optionalCartMock().get();

        when(userService.getCurrentUser()).thenReturn(user);
        when(this.cartDAO.findCartByUser(any(UserModel.class))).thenReturn(Optional.of(cart));
        CartModel result = this.cartService.getCartByCurrentUser();

        assertEquals(cart, result);
        verify(cartDAO).findCartByUser(user);
        verify(cartDAO, never()).save(any(CartModel.class));
    }

    // getCartByCurrentUser method
    @Test
    public void testGetCartByCurrentUser_CartDoesNotExists() {
        UserModel user = UserDataProvider.optionalUserMock().get();
        CartModel cart = CartDataProvider.optionalCartMock().get();

        when(userService.getCurrentUser()).thenReturn(user);
        when(this.cartDAO.findCartByUser(any(UserModel.class))).thenReturn(Optional.empty());
        when(this.cartDAO.save(any(CartModel.class))).thenReturn(cart);
        CartModel result = this.cartService.getCartByCurrentUser();

        assertEquals(cart, result);
        verify(cartDAO).findCartByUser(user);
        verify(cartDAO).save(any(CartModel.class));
    }

    // addToCart method
    @Test
    public void testAddToCart_ProductExists() {
        Integer productId = 1;  // ID of the product to be added to the cart
        int quantity = 10;  // Quantity to be added to the existing product
        CartModel cart = CartDataProvider.optionalCartMock().get();  // Mock cart object
        ProductModel existingProduct = ProductDataProvider.productMock().get();  // Mock existing product object

        // Create an existing item in the cart
        CartItemModel existingItem = new CartItemModel(cart, existingProduct, 1);
        cart.addCartItem(existingItem);  // Add the existing item to the cart

        // Mock user object
        UserModel user = UserDataProvider.optionalUserMock().get();
        // Mock getCurrentUser method in userService to return the mock user
        when(this.userService.getCurrentUser()).thenReturn(user);

        // Mock save method in cartDAO to return the cart object
        when(this.cartDAO.save(any(CartModel.class))).thenReturn(cart);
        // Mock findCartByUser method in cartDAO to return the mock cart
        when(this.cartDAO.findCartByUser(any(UserModel.class))).thenReturn(Optional.of(cart));
        // Mock findById method in productDAO to return the mock existing product
        when(this.productDAO.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Call addToCart method in cartService with the specified product ID and quantity
        this.cartService.addToCart(productId, quantity);

        // Assert that the quantity of the existing item in the cart has been updated correctly
        assertEquals(11, existingItem.getQuantity());
        // Verify that the save method in cartDAO was called once with the updated cart
        verify(cartDAO).save(cart);
    }

    @Test
    public void testAddToCart_ProductNotInCart() {
        Integer productId = 1;
        int quantity = 5;

        // Mocking objects
        UserModel user = UserDataProvider.optionalUserMock().get();
        CartModel cart = CartDataProvider.optionalCartMock().get();
        ProductModel newProduct = ProductDataProvider.productMock().get();

        // Mock the method getCurrentUser in userService to return the mock user
        when(userService.getCurrentUser()).thenReturn(user);

        // Mock the method findCartByUser in cartDAO to return the mock cart
        when(cartDAO.findCartByUser(any(UserModel.class))).thenReturn(Optional.of(cart));

        // Mock the method findById in productDAO to return the mock product
        when(productDAO.findById(productId)).thenReturn(Optional.of(newProduct));

        // Mock the method save in cartDAO to return the updated cart
        when(cartDAO.save(any(CartModel.class))).thenAnswer(invocation -> {
            CartModel savedCart = invocation.getArgument(0);
            return savedCart;
        });

        // Run the method addToCart
        cartService.addToCart(productId, quantity);

        // Verify the behavior
        assertEquals(1, cart.getCartItems().size());
        CartItemModel cartItem = cart.getCartItems().get(0);
        assertEquals(productId, cartItem.getProduct().getId());
        assertEquals(quantity, cartItem.getQuantity());

        // Verify that save was called once with the updated cart
        verify(cartDAO, times(1)).save(cart);
        verify(productDAO, times(1)).findById(productId);
    }

    @Test
    public void testAddToCart_ProductNotFound() {
        Integer productId = 1;
        int quantity = 5;
        UserModel user = UserDataProvider.optionalUserMock().get();
        CartModel cart = CartDataProvider.optionalCartMock().get();

        // Mock the method getCurrentUser() in userService to return mock user
        when(this.userService.getCurrentUser()).thenReturn(user);

        // Mock the method findById in productDAO to return Optional.empty(), indicating product not found
        when(this.productDAO.findById(productId)).thenReturn(Optional.empty());

        // Mock the method findCartByUser to return an existing cart to avoid creating a new cart
        when(this.cartDAO.findCartByUser(user)).thenReturn(Optional.of(cart));

        // Assert that calling addToCart with non-existing product throws a RuntimeException
        assertThrows(RuntimeException.class, () -> cartService.addToCart(productId, quantity));

        // Verify that cartDAO.save(any(CartModel.class)) was never invoked
        verify(this.cartDAO, never()).save(any(CartModel.class));
    }

    // updateCart method
    @Test
    public void testUpdateCart_IncrementQuantity() {
        Long itemId = 1L;
        String operation = "sum";
        UserModel user = UserDataProvider.optionalUserMock().get();
        CartModel cart = CartDataProvider.optionalCartMock().get();
        CartItemModel existingItem = CartItemModel.builder()
                .cart(cart)
                .product(ProductDataProvider.productMock().get())
                .quantity(2)
                .build();

        // Mock the method getCurrentUser in userService to return mock user
        when(this.userService.getCurrentUser()).thenReturn(user);

        // Mock the method findCartByUser in cartDAO class to return mock
        when(this.cartDAO.findCartByUser(any(UserModel.class))).thenReturn(Optional.of(cart));

        // Mock the method findCartItemModelById in cartServiceImpl to return existsProduct
        when(this.cartItemDAO.findCartItemModelById(anyLong())).thenReturn(Optional.of(existingItem));

        // Call the method under test
        this.cartService.updateCart(itemId, operation);

        // Verify the quantity is incremented
        assertEquals(3, existingItem.getQuantity());

        // Verify the save method is called on cartDAO with the update cart
        verify(this.cartDAO).save(cart);

        // Verify deleteByIde is not called in cartItemDAO
        verify(this.cartItemDAO, never()).deleteById(itemId);
    }

    @Test
    public void testUpdateCart_DecrementQuantity() {
        Long itemId = 1L;
        String operation = "substract";
        UserModel user = UserDataProvider.optionalUserMock().get();
        CartModel cart = CartDataProvider.optionalCartMock().get();
        CartItemModel existingItem = CartItemModel.builder()
                .cart(cart)
                .product(ProductDataProvider.productMock().get())
                .quantity(2)
                .build();

        // Mock the method getCurrentUser in userService to return mock user
        when(this.userService.getCurrentUser()).thenReturn(user);

        // Mock the method findCartByUser in cartDAO class to return mock
        when(this.cartDAO.findCartByUser(any(UserModel.class))).thenReturn(Optional.of(cart));

        // Mock the method findCartItemModelById in cartServiceImpl to return existsProduct
        when(this.cartItemDAO.findCartItemModelById(anyLong())).thenReturn(Optional.of(existingItem));

        // Call the method under test
        this.cartService.updateCart(itemId, operation);

        // Verify the quantity is incremented
        assertEquals(1, existingItem.getQuantity());

        // Verify the save method is called on cartDAO with the update cart
        verify(this.cartDAO).save(cart);

        // Verify deleteByIde is not called in cartItemDAO
        verify(this.cartItemDAO, never()).deleteById(itemId);
    }

    @Test
    public void testUpdateCart_DecrementQuantityToZero() {
        Long itemId = 1L;
        String operation = "substract";
        UserModel user = UserDataProvider.optionalUserMock().get();
        CartModel cart = CartDataProvider.optionalCartMock().get();
        CartItemModel existingItem = CartItemModel.builder()
                .cart(cart)
                .product(ProductDataProvider.productMock().get())
                .quantity(1)
                .build();

        // Mock the method getCurrentUser in userService to return mock user
        when(this.userService.getCurrentUser()).thenReturn(user);

        // Mock the method findCartByUser in cartDAO class to return mock
        when(this.cartDAO.findCartByUser(any(UserModel.class))).thenReturn(Optional.of(cart));

        // Mock the method findCartItemModelById in cartServiceImpl to return existsProduct
        when(this.cartItemDAO.findCartItemModelById(anyLong())).thenReturn(Optional.of(existingItem));

        // Call the method under test
        this.cartService.updateCart(itemId, operation);

        // Verify the quantity is incremented
        assertEquals(0, existingItem.getQuantity());

        // Verify the save method is called on cartDAO with the update cart
        verify(this.cartDAO).save(cart);

        // Verify deleteByIde is not called in cartItemDAO
        verify(this.cartItemDAO).deleteById(itemId);
    }

    @Test
    public void testUpdateCart_ItemNotFound() {
        Long itemId = 1L;
        String operation = "sum";
        UserModel user = UserDataProvider.optionalUserMock().get();
        CartModel cart = CartDataProvider.optionalCartMock().get();

        // Mock the method getCurrentUser in userService to return mock user
        when(this.userService.getCurrentUser()).thenReturn(user);

        // Mock the method findCartByUser in cartDAO class to return mock cart
        when(this.cartDAO.findCartByUser(any(UserModel.class))).thenReturn(Optional.of(cart));

        // Mock the method findCartItemModelById in cartItemDAO to return empty
        when(this.cartItemDAO.findCartItemModelById(anyLong())).thenReturn(Optional.empty());

        // Assert that the RuntimeException is thrown when the item is not found
        assertThrows(RuntimeException.class, () -> {
           cartService.updateCart(itemId, operation);
        });

        // Verify that save method en cardDAO is never called
        verify(this.cartDAO, never()).save(cart);
    }

    // checkout method
    @Test
    public void testCheckout() {
        CartModel cart = CartDataProvider.optionalCartMock().get();
        UserModel user = UserDataProvider.optionalUserMock().get();

        when(this.userService.getCurrentUser()).thenReturn(user);
        when(this.cartDAO.findCartByUser(any(UserModel.class))).thenReturn(Optional.of(cart));
        this.cartService.checkout();

        verify(orderService).createOrderFromCart(cart); // Verify order is created from cart
        assertTrue(cart.getCartItems().isEmpty());
        verify(cartDAO).save(cart);
    }

    // calculateTotalPrice method
    @Test
    public void testCalculateTotalPrice_NotNullItems() {
        CartModel cart = CartDataProvider.optionalCartMockWithItems().get();

        BigDecimal result = this.cartService.calculateTotalPrice(cart);
        assertEquals(BigDecimal.valueOf(980), result);
    }

    @Test
    public void testCalculateTotalPrice_NullItems() {
        CartModel cart = CartDataProvider.optionalCartNullItemsMock().get();

        BigDecimal result = this.cartService.calculateTotalPrice(cart);
        assertEquals(BigDecimal.valueOf(0), result);
    }
}
