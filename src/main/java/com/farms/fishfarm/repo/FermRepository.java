package com.farms.fishfarm.repo;

import com.farms.fishfarm.entities.Ferm;
import com.farms.fishfarm.entities.Firm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FermRepository extends JpaRepository<Ferm,Long> {

 Optional <Ferm> findByName(String name);
 Optional <Ferm> findById(Long id);
 List<Ferm> findByFirmId(Firm firmId);
 
}