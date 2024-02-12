package com.farms.fishfarm.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.farms.fishfarm.entities.Ferm;
import com.farms.fishfarm.entities.Firm;
import com.farms.fishfarm.entities.RealSadok;
import com.farms.fishfarm.entities.VirtualSadok;
import com.farms.fishfarm.repo.FermRepository;
import com.farms.fishfarm.repo.FirmRepository;
import com.farms.fishfarm.repo.RealSadokRepository;
import com.farms.fishfarm.repo.UserRepository;
import com.farms.fishfarm.repo.VirtualSadokRepository;

@Service
public class FermService {
   
    
    private FermRepository fermRepository;
    private FirmRepository firmRepository;
    private RealSadokRepository realSadokRepository;
    private VirtualSadokRepository virtualSadokRepository;


    public FermService(UserRepository userRepository, FermRepository fermRepository, FirmRepository firmRepository, RealSadokRepository realSadokRepository, VirtualSadokRepository virtualSadokRepository) {
        this.fermRepository = fermRepository;
        this.firmRepository = firmRepository;
        this.realSadokRepository = realSadokRepository;
        this.virtualSadokRepository = virtualSadokRepository;
    }

    public Ferm getFermById(Long id){
        return fermRepository.findById(id).orElse(null);
    }


}
