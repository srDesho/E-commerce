package com.cristianml.security.service;

import com.cristianml.security.model.UserModel;
import com.cristianml.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = this.userRepository.findUserModelByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " don't exists."));

        // Permits granted(autorizaciones permitidas)
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userModel.getRoleList()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        // Add permissions
        userModel.getRoleList().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userModel.getUsername(),
                userModel.getPassword(),
                userModel.isEnable(),
                userModel.isAccountNoExpired(),
                userModel.isCredentialNoExpired(),
                userModel.isAccountNoLocked(),
                authorityList);
    }
}
