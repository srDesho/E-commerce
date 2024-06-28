package com.cristianml.persistence.impl;

import com.cristianml.DataProvider;
import com.cristianml.security.model.UserModel;
import com.cristianml.security.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        when(this.userDAO.findAll()).thenReturn(DataProvider.userListMock());
        List<UserModel> result = this.userDAO.findAll();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).getId());
        assertEquals("johndoe@gmail.com", result.get(0).getUsername());
        assertEquals("123456", result.get(0).getPassword());
        assertEquals("johndoe@gmail.com", result.get(0).getUsername());
        assertEquals(true, result.get(0).isEnable());
        assertEquals(true, result.get(0).isAccountNoLocked());
        assertEquals(true, result.get(0).isCredentialNoExpired());
    }



}
