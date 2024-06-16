package com.cristianml.security.repository;

import com.cristianml.security.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {

    // Find by username(email)
    UserModel findUserModelByUsername(String username);

}
