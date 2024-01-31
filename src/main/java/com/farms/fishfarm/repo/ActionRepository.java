package com.farms.fishfarm.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.farms.fishfarm.entities.Action;

@Repository
public interface ActionRepository extends JpaRepository<Action,Long> {
    Optional <Action> findByName(String name);
    
}
