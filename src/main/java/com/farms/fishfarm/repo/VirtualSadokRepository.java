package com.farms.fishfarm.repo;

import com.farms.fishfarm.entities.VirtualSadok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VirtualSadokRepository extends JpaRepository<VirtualSadok,Long> {

 Optional <VirtualSadok> findByName(String name);
}