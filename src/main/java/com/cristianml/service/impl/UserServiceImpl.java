package com.cristianml.service.impl;

import com.cristianml.persistence.IUserDAO;
import com.cristianml.security.model.UserModel;
import com.cristianml.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    // Method for get the current user
    public UserModel getCurrentUser() {
        String username = getCurrentUsername(); // We call the method created below to get current username
        Optional<UserModel> userOptional = userDAO.findByUsername(username);
        return userOptional.orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Method to get the username of the current user
    private String getCurrentUsername() {
        // The Principal object stores the authenticated user's details
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // This method return the object Principal
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Check if the principal object exists
        if (principal instanceof UserDetails) {
            // Retrieve(recuperar) the username from UserDetails interface
            return ((UserDetails) principal).getUsername();
        } else {
            // If the principal object is not an instance of UserDetails,
            // it might be empty or represent an anonymous user
            return principal.toString();
        }
    }


}
