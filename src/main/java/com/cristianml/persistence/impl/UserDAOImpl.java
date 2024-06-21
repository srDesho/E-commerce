package com.cristianml.persistence.impl;

import com.cristianml.persistence.IUserDAO;
import com.cristianml.security.model.UserModel;
import com.cristianml.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDAOImpl implements IUserDAO {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserModel> findAll() {
        return (List<UserModel>) this.userRepository.findAll();
    }

    @Override
    public Optional<UserModel> findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public Optional<UserModel> findByUsername(String username) {
        return userRepository.findUserModelByUsername(username);
    }

    @Override
    public UserModel createUser(UserModel user) {
        return this.userRepository.save(user);
    }

    @Override
    public void save(UserModel user) {
        this.userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }
}
