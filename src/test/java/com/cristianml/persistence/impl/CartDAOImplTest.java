package com.cristianml.persistence.impl;

import com.cristianml.dataProvider.CartDataProvider;
import com.cristianml.dataProvider.UserDataProvider;
import com.cristianml.models.CartModel;
import com.cristianml.repository.CartRepository;
import com.cristianml.security.model.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartDAOImplTest {

    @Mock
    CartRepository cartRepository;

    @InjectMocks
    CartDAOImpl cartDAO;

    // save method
    @Test
    public void testSave() {
        CartModel cart = CartDataProvider.optionalCartMock().get();

        when(this.cartRepository.save(any(CartModel.class))).thenReturn(cart);
        CartModel result = this.cartDAO.save(cart);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    // findCartByUser method
    @Test
    public void testFindCartByUser() {
        UserModel user = UserDataProvider.optionalUserMock().get();
        CartModel cart = CartDataProvider.optionalCartMock().get();

        when(this.cartRepository.findCartModelByUser(any(UserModel.class))).thenReturn(Optional.of(cart));
        Optional<CartModel> result = this.cartDAO.findCartByUser(user);

        assertEquals(user.getId(), result.get().getUser().getId());
    }
}
