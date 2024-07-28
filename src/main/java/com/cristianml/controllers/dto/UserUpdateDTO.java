package com.cristianml.controllers.dto;

import jakarta.persistence.Column;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDTO {

    @NotEmpty(message = "cannot be empty.")
    private String name;
    @NotEmpty(message = "cannot be empty.")
    private String address;
    private String profileImage;
    @NotEmpty(message = "cannot be empty.")
    @Column(nullable = true)
    private String pincode;
    private String phoneNumber;
}