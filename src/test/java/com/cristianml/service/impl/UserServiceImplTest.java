package com.cristianml.service.impl;

import com.cristianml.dataProvider.UserDataProvider;
import com.cristianml.persistence.impl.UserDAOImpl;
import com.cristianml.security.model.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    UserDAOImpl userDAO;

    @InjectMocks
    UserServiceImpl userService;

    // findAll method
    @Test
    public void testFindAll() {
        // When
        when(this.userDAO.findAll()).thenReturn(UserDataProvider.userListMock());
        List<UserModel> result = this.userService.findAll();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).getId());
        assertEquals("johndoe@gmail.com", result.get(0).getUsername());
        assertEquals("123456", result.get(0).getPassword());
        assertTrue(result.get(0).isEnable());
        assertTrue(result.get(0).isAccountNoLocked());
        assertTrue(result.get(0).isCredentialNoExpired());
    }

    // findById method
    @Test
    public void testFindById() {
        // Given
        Long id = 1L;

        // When
        when(this.userDAO.findById(anyLong()))
                .thenReturn(UserDataProvider.optionalUserMock());
        Optional<UserModel> result = this.userService.findById(id);

        // Then
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Cristian Montaño", result.get().getName());
        assertEquals("cristianml@gmail.com", result.get().getUsername());
        assertEquals("123456", result.get().getPassword());
        assertEquals("cristianml@gmail.com", result.get().getUsername());
        assertTrue(result.get().isEnable());
        assertTrue(result.get().isAccountNoLocked());
        assertTrue(result.get().isCredentialNoExpired());
    }

    // Method to createUser
    @Test
    public void testCreateUser() {
        UserModel userModel = UserDataProvider.optionalUserMock().get();

        when(this.userDAO.createUser(any(UserModel.class))).thenReturn(UserDataProvider.createUserMock(userModel));
        UserModel result = this.userService.createUser(userModel);

        // Verificar los atributos del usuario creado
        assertEquals("Cristian Montaño", result.getName());
        assertEquals("cristianml@gmail.com", result.getUsername());
        assertEquals("123456", result.getPassword());
        assertEquals("Plan 3000", result.getAddress());
        assertEquals("profile.png", result.getProfileImage());
        assertEquals("123", result.getPincode());
        assertTrue(result.isEnable());
        assertTrue(result.isAccountNoLocked());
        assertTrue(result.isAccountNoExpired());
        assertTrue(result.isCredentialNoExpired());
        assertNotNull(result.getRoleList());
        assertEquals(2, result.getOrderList().size());
    }

    // Method save
    @Test
    public void testSave() {
        UserModel userModel = UserDataProvider.optionalUserMock().get();

        this.userService.save(userModel);

        ArgumentCaptor<UserModel> argumentCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(this.userDAO).save(argumentCaptor.capture());
        assertEquals("Cristian Montaño", argumentCaptor.getValue().getName());
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;

        this.userService.deleteById(id);

        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.userDAO).deleteById(anyLong());
        verify(this.userDAO).deleteById(longArgumentCaptor.capture());
        assertEquals(1L, longArgumentCaptor.getValue());
    }

}
