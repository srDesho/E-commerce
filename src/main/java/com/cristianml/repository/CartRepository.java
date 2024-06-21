package com.cristianml.repository;

import com.cristianml.models.CartModel;
import com.cristianml.security.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<CartModel, Long> {

    Optional<CartModel> findCartModelByUser(UserModel currentUser);


}
