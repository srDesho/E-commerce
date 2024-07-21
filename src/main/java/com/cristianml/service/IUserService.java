package com.cristianml.service;

import com.cristianml.security.model.RoleEnum;
import com.cristianml.security.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<UserModel> findAll();
    List<UserModel> findAllUsersByRole(RoleEnum roleEnum);

    Optional<UserModel> findById(Long id);

    UserModel createUser(UserModel user);

    UserModel save(UserModel user);

    void deleteById(Long id);

    boolean existsByUsername(String username);
}
