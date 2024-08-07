package com.cristianml.persistence;

import com.cristianml.security.model.RoleEnum;
import com.cristianml.security.model.RoleModel;
import com.cristianml.security.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface IUserDAO {

    List<UserModel> findAll();
    List<UserModel> findAllUsersByRole(RoleEnum roleEnum);

    Optional<UserModel> findById(Long id);

    Optional<UserModel> findByUsername(String username);

    UserModel createUser(UserModel user);
    UserModel save(UserModel user);

    void deleteById(Long id);

    boolean existsByUsername(String username);
}
