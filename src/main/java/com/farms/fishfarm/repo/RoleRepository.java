package com.farms.fishfarm.repo;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.farms.fishfarm.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional <Role> findByName(String name);
    @Procedure("delete_user_role")
    void deleteRole(Long idU,Long udR);
}
