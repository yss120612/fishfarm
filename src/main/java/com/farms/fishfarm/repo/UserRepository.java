package com.farms.fishfarm.repo;

import com.farms.fishfarm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import com.farms.fishfarm.entities.Firm;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

 Optional <User> findByUsername(String username);
 List<User> findByFirmId(Firm firmId);
}