package com.farms.fishfarm.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.farms.fishfarm.entities.Ferm;
import com.farms.fishfarm.entities.Firm;
import com.farms.fishfarm.repo.FermRepository;
import com.farms.fishfarm.repo.FirmRepository;
import com.farms.fishfarm.repo.UserRepository;

@Service
public class FirmService {
   
    private UserRepository userRepository;
    private FermRepository fermRepository;
    private FirmRepository firmRepository;


    public FirmService(UserRepository userRepository, FirmRepository firmRepository) {
        this.userRepository = userRepository;
        this.firmRepository = firmRepository;
    }

    public boolean addFirm(Firm firm){
        Firm ffirm = null;
        Long id = 0L;
        if (firm.getId() != null && firm.getId() > 0) {
            ffirm = firmRepository.findById(firm.getId()).orElse(null);
            if (ffirm != null)
                id = ffirm.getId();
        }

        ffirm = firmRepository.findByShortname(firm.getShortname()).orElse(null);

        if (ffirm != null && id > 0 && !id.equals(ffirm.getId())  //поменяли имя на то которое уже есть в БД у другой фирмы
        || ffirm != null && id == 0) {                      //хотим добавить фирму с именем, уже существующем в БД
            // throw new NameAlreadyBoundException()
            return false;
        } else {
            firmRepository.save(firm);// insert-update firm
        }
        return true;

    }

    public boolean addFirm(String name,String fullName, String inn, String addr ){
        Firm firm = new Firm();
        firm.setShortname(name);
        firm.setFullname(fullName);
        firm.setAddress(addr);
        firm.setInn(inn);
        return addFirm(firm);

    }

    public boolean addFerm(Ferm ferm){
        Firm fferm = null;
        Long id = 0L;
        if (ferm.getId() != null && ferm.getId() > 0) {
            fferm = firmRepository.findById(ferm.getId()).orElse(null);
            if (fferm != null)
                id = fferm.getId();
        }

        fferm = firmRepository.findByShortname(ferm.getName()).orElse(null);

        if (fferm != null && id > 0 && !id.equals(fferm.getId())  //поменяли имя на то которое уже есть в БД у другой фирмы
        || fferm != null && id == 0) {                      //хотим добавить ферму с именем, уже существующем в БД
            // throw new NameAlreadyBoundException()
            return false;
        } else {
            fermRepository.save(ferm);// insert-update firm
        }
        return true;

    }

    public boolean addFerm(String name, Long firmId){
        if (firmId==null) return false;
        Ferm ferm = new Ferm();
        ferm.setName(name);
        Firm firm=firmRepository.findById(firmId).orElse(null);
        if (firm==null) return false;
        Set<Ferm> ferms=firm.getFerms();
        if (ferms==null) ferms=new HashSet<>();
        boolean result=addFerm(ferm);
        if (!result) return false;
        ferm=fermRepository.findByName(name).orElse(null);
        if (ferm==null) return false;
        ferms.add(ferm);
        firm.setFerms(ferms);
        firmRepository.save(firm);
        return true;
    }

}
