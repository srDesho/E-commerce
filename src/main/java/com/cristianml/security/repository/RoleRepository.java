package com.cristianml.security.repository;

import com.cristianml.security.model.RoleEnum;
import com.cristianml.security.model.RoleModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<RoleModel, Long> {
    boolean existsByRoleEnum(RoleEnum roleEnum);
    List<RoleModel> findRoleEntitiesByRoleEnumIn(List<String> roleList);
}
