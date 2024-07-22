package com.cristianml.service.converter;

import com.cristianml.controllers.dto.UserDTO;
import com.cristianml.security.model.UserModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private final PasswordEncoder passwordEncoder;

    public UserConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO toDTO(UserModel userModel) {
        return UserDTO.builder()
                .name(userModel.getName())
                .username(userModel.getUsername())
                .password(passwordEncoder.encode(userModel.getPassword()))
                .address(userModel.getAddress())
                .profileImage(userModel.getProfileImage())
                .pincode(userModel.getPincode())
                .phoneNumber(userModel.getPhoneNumber())
                .build();
    }

    public UserModel toModel(UserDTO userDTO) {
        return UserModel.builder()
                .name(userDTO.getName())
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .address(userDTO.getAddress())
                .profileImage(userDTO.getProfileImage())
                .pincode(userDTO.getPincode())
                .phoneNumber(userDTO.getPhoneNumber())
                .build();
    }

}
