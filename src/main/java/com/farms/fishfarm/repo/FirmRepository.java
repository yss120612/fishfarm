package com.farms.fishfarm.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.farms.fishfarm.entities.Firm;

@Repository
public interface FirmRepository extends JpaRepository<Firm,Long> {
    Optional <Firm> findByShortname(String name);
    Optional <Firm> findByInn(String name);
}
