package com.cristianml.security.repository;

import com.cristianml.security.model.RoleEnum;
import com.cristianml.security.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {

    // Find by username(email)
    Optional<UserModel> findUserModelByUsername(String username);

    // Find all users by role admin
    List<UserModel> findAllByRoleList_RoleEnum(RoleEnum roleEnum);

    boolean existsByUsername(String username);

}
