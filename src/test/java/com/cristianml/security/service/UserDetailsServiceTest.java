package com.cristianml.security.service;

import com.cristianml.security.model.PermissionModel;
import com.cristianml.security.model.RoleEnum;
import com.cristianml.security.model.RoleModel;
import com.cristianml.security.model.UserModel;
import com.cristianml.security.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Test
    public void testLoadUserByUsername_UserExists() {

        // Mock user
        UserModel user = UserModel.builder()
                .username("user")
                .password("123456")
                .isEnable(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .accountNoLocked(true)
                .build();

        // Mock roles and permissions
        RoleModel role = new RoleModel();
        role.setRoleEnum(RoleEnum.ADMIN);

        PermissionModel permission = new PermissionModel();
        permission.setName("READ");
        role.setPermissionList(Set.of(permission));
        user.setRoleList(Set.of(role));

        when(this.userRepository.findUserModelByUsername(anyString())).thenReturn(Optional.of(user));
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getUsername());

        assertNotNull(userDetails);
        assertEquals("user", userDetails.getUsername());
        assertEquals(2, userDetails.getAuthorities().size());
    }

    @Test
    public void testLoadUserByUsername_UserDoesNotExists() {
        when(this.userRepository.findUserModelByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
           this.userDetailsService.loadUserByUsername("user");
        });
    }
}
