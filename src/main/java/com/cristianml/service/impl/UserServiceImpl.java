package com.cristianml.service.impl;

import com.cristianml.persistence.IUserDAO;
import com.cristianml.security.model.UserModel;
import com.cristianml.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDAO userDAO;

    @Override
    public List<UserModel> findAll() {
        return this.userDAO.findAll();
    }

    @Override
    public Optional<UserModel> findById(Long id) {
        return this.userDAO.findById(id);
    }

    @Override
    public UserModel createUser(UserModel user) {
        return this.userDAO.createUser(user);
    }

    @Override
    public void save(UserModel user) {
        this.userDAO.save(user);
    }

    @Override
    public void deleteById(Long id) {
        this.userDAO.deleteById(id);
    }


}
