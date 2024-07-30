package com.cristianml.service.impl;

import com.cristianml.persistence.IUserDAO;
import com.cristianml.security.model.RoleEnum;
import com.cristianml.security.model.UserModel;
import com.cristianml.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
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
    public List<UserModel> findAllUsersByRole(RoleEnum roleEnum) {
        return this.userDAO.findAllUsersByRole(roleEnum);
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
    public UserModel save(UserModel user) {
        this.userDAO.save(user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        this.userDAO.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.userDAO.existsByUsername(username);
    }

    // Custom exception for unauthenticated access
    public class UnauthenticatedUserException extends RuntimeException {
        public UnauthenticatedUserException(String message) {
            super(message);
        }
    }

    // Method for getting the current user
    public UserModel getCurrentUser() {
        String username = getCurrentUsername(); // Get the username of the currently authenticated user
        Optional<UserModel> userOptional = userDAO.findByUsername(username); // Find the user by username in the database
        return userOptional.orElseThrow(() -> new RuntimeException("User not found")); // Throw exception if user not found
    }

    // Method to get the username of the current user
    public String getCurrentUsername() {
        // The Principal object stores the authenticated user's details
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // Retrieve the principal object

        // Check if the principal object exists and is an instance of UserDetails
        if (principal instanceof UserDetails) {
            // Retrieve the username from UserDetails interface
            return ((UserDetails) principal).getUsername();
        } else {
            // If the principal object is not an instance of UserDetails,
            // it might be empty or represent an anonymous user
            throw new UnauthenticatedUserException("User not authenticated"); // Throw custom exception for unauthenticated access
        }
    }

    // This method is to ask if a user is authenticated.
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"));
    }

}
