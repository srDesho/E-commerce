package com.cristianml.persistence.impl;


import com.cristianml.dataProvider.UserDataProvider;
import com.cristianml.security.model.UserModel;
import com.cristianml.security.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDAOImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserDAOImpl userDAO;

    // findAll method
    @Test
    public void testFindAll() {
        // When
        when(this.userRepository.findAll()).thenReturn(UserDataProvider.userListMock());
        List<UserModel> result = this.userDAO.findAll();

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
        when(this.userRepository.findById(anyLong()))
                .thenReturn(UserDataProvider.optionalUserMock());
        Optional<UserModel> result = this.userDAO.findById(id);

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

    // Method findByUsername
    @Test
    public void testFindByUsername() {
        // Given
        String username = "cristianml@gmail.com";

        // When
        when(this.userRepository.findUserModelByUsername(anyString())).thenReturn(UserDataProvider.optionalFindByUsernameMock(username));
        Optional<UserModel> result = userDAO.findByUsername(username);

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
        UserModel result = this.userDAO.createUser(userModel);

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

        this.userDAO.save(userModel);

        ArgumentCaptor<UserModel> argumentCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(this.userRepository).save(argumentCaptor.capture());
        assertEquals("Cristian Montaño", argumentCaptor.getValue().getName());
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;

        this.userDAO.deleteById(id);

        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.userRepository).deleteById(anyLong());
        verify(this.userRepository).deleteById(longArgumentCaptor.capture());
        assertEquals(1L, longArgumentCaptor.getValue());
    }
}
