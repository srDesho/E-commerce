package com.cristianml.service;

import com.cristianml.security.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<UserModel> findAll();

    Optional<UserModel> findById(Long id);

    UserModel createUser(UserModel user);

    void save(UserModel user);

    void deleteById(Long id);
}
