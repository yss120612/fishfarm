package com.farms.fishfarm.repo;

import com.farms.fishfarm.entities.User;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.List;
import com.farms.fishfarm.entities.Firm;
import com.farms.fishfarm.entities.Role;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

 Optional <User> findByUsername(String username);
 List<User> findByFirmId(Firm firmId);
 //List<User> findByFirmIdAndRoleIn(Firm firmId, Set<Role> roles);
 @Procedure("delete_user_role")
    void deleteUser(Long idU,Long idR);
    
// @Query(value = "CALL FIND_CARS_AFTER_YEAR(:year_in);", nativeQuery = true)
// List<User> getUsersInRole(@Param("year_in") Integer year_in);

@Query(value="call select_user_for_role(:id_firma,:roles_n);", nativeQuery=true)
List<User> getUsersInRole(@Param("id_firma") Long firmId, @Param("roles_n") String roleString);

}