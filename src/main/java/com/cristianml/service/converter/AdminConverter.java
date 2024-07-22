package com.cristianml.service.converter;

import com.cristianml.controllers.dto.UserDTO;
import com.cristianml.security.model.UserModel;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminConverter {

    private final PasswordEncoder passwordEncoder;

    public AdminConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // Converts UserModel to UserDTO, including only relevant attributes
    public UserDTO toDTO(UserModel userModel) {
        return UserDTO.builder()
                .name(userModel.getName())
                .phoneNumber(userModel.getPhoneNumber())
                .username(userModel.getUsername())
                .password(userModel.getPassword()) // Omit if not needed
                .build();
    }

    // Converts UserDTO to UserModel, setting only the attributes from the form
    public UserModel toEntity(UserDTO userDTO) {
        UserModel userModel = new UserModel();
        userModel.setName(userDTO.getName());
        userModel.setPhoneNumber(userDTO.getPhoneNumber());
        userModel.setUsername(userDTO.getUsername());
        userModel.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userModel;
    }

}
