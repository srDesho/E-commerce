package com.cristianml.service.impl;

import com.cristianml.persistence.impl.UserDAOImpl;
import com.cristianml.security.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplCurrentUserTest {

    @Mock
    private UserDetails userDetails;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private UserDAOImpl userDAO;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    public void testGetCurrentUsername_UserDetailsPrincipal() {
        // Arrange
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser");

        // Act
        String username = userService.getCurrentUsername();

        // Assert
        assertEquals("testuser", username);
    }

    @Test
    public void testGetCurrentUsername_OtherPrincipal() {
        // Arrange
        Object principal = "anonymousUser"; // or any other type
        when(authentication.getPrincipal()).thenReturn(principal);

        // Act
        String username = userService.getCurrentUsername();

        // Assert
        assertEquals("anonymousUser", username); // or assert for the appropriate principal.toString()
    }

    @Test
    public void testGetCurrentUser_UserFound() {
        // Arrange
        String username = "testuser";
        UserModel expectedUser = new UserModel();
        expectedUser.setUsername(username);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(username);
        when(userDAO.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        // Act
        UserModel actualUser = userService.getCurrentUser();

        // Assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testGetCurrentUser_UserNotFound() {
        // Arrange
        String username = "testuser";

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(username);
        when(userDAO.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.getCurrentUser());
    }
}