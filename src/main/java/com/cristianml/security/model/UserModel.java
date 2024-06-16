package com.cristianml.security.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    @NotEmpty(message = "is empty.")
    @Column(nullable = false)
    private String name;
    @NotNull(message = "is empty.")
    @Column(unique = true)
    @NotEmpty(message = "cannot be empty.")
    private String username;
    @NotNull(message = "cannot be empty")
    private String password;
    private String address;

    @Column(name = "profile_image")
    private String profileImage;
    @NotNull(message = "cannot be empty")
    private String pincode;

    @Column(name = "is_enable")
    private boolean isEnable;

    @Column(name = "account_no_expired")
    private boolean accountNoExpired;

    @Column(name = "account_no_locked")
    private boolean accountNoLocked;

    @Column(name = "credential_no_expired")
    private boolean credentialNoExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleModel> roleList = new HashSet<>();
}
