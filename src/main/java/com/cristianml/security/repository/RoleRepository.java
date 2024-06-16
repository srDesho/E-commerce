package com.cristianml.security.repository;

import com.cristianml.security.model.RoleModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleModel, Long> {
}
