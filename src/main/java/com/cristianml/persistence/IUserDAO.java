package com.cristianml.persistence;

import com.cristianml.security.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface IUserDAO {

    List<UserModel> findAll();

    Optional<UserModel> findById(Long id);

    Optional<UserModel> findByUsername(String username);

    UserModel createUser(UserModel user);
    void save(UserModel user);

    void deleteById(Long id);


}
