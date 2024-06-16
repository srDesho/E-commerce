package com.cristianml.security.repository;

import com.cristianml.security.model.PermissionModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends CrudRepository<PermissionModel, Long> {
}
