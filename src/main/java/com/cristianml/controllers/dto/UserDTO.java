package com.cristianml.controllers.dto;

import com.cristianml.security.model.RoleModel;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    @NotEmpty(message = "is empty.")
    private String name;
    @NotNull(message = "is empty.")
    @NotEmpty(message = "cannot be empty.")
    private String username;
    @NotNull(message = "cannot be empty")
    private String password;
    private String address;
    private String profileImage;
    @Column(nullable = true)
    private String pincode;
    @NotNull(message = "cannot be empty")
    private String phoneNumber;
    private boolean isEnable;
    private boolean accountNoExpired;
    private boolean accountNoLocked;
    private boolean credentialNoExpired;
    private Set<RoleModel> roleList;
}
