package com.cristianml.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    @NotNull(message = "is empty.")
    private String username;
    @Column(unique = true)
    private String email;
    @NotNull(message = "is empty")
    private String password;
    private Integer state;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<AuthorizeModel> authorizeList = new ArrayList<>();

}
