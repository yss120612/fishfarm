package com.farms.fishfarm.repo;

import com.farms.fishfarm.entities.RealSadok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RealSadokRepository extends JpaRepository<RealSadok,Long> {

 Optional <RealSadok> findByName(String name);
}