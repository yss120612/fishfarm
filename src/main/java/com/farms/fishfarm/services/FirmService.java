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
public class FirmService {
   
    private UserRepository userRepository;
    private FermRepository fermRepository;
    private FirmRepository firmRepository;
    private RealSadokRepository realSadokRepository;
    private VirtualSadokRepository virtualSadokRepository;


    public FirmService(UserRepository userRepository, FermRepository fermRepository, FirmRepository firmRepository, RealSadokRepository realSadokRepository, VirtualSadokRepository virtualSadokRepository) {
        this.userRepository = userRepository;
        this.fermRepository = fermRepository;
        this.firmRepository = firmRepository;
        this.realSadokRepository = realSadokRepository;
        this.virtualSadokRepository = virtualSadokRepository;
    }

    public Firm findById(Long id){
        if (id==null) return null;
        return firmRepository.findById(id).orElse(null);
    }

    public Firm findByName(String name){
        if (name==null) return null;
        return firmRepository.findByShortname(name).orElse(null);
    }

    public void deleteFirm(Firm firm){
        firmRepository.delete(firm);
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


    public List<Firm> getFirms(){
        return firmRepository.findAll();
    }


    public boolean addFerm(Ferm ferm){
        Ferm fferm = null;
        Long id = 0L;
        if (ferm.getId() != null && ferm.getId() > 0) {
            fferm = fermRepository.findById(ferm.getId()).orElse(null);
            if (fferm != null)
                id = fferm.getId();
        }

        fferm = fermRepository.findByName(ferm.getName()).orElse(null);

        if (fferm != null && id > 0 && !id.equals(fferm.getId())  //поменяли имя на то которое уже есть в БД у другой фирмы
        || fferm != null && id == 0) {                      //хотим добавить ферму с именем, уже существующем в БД
            // throw new NameAlreadyBoundException()
            return false;
        } else {
            if (ferm.getFirmId()==null) return false;
            //Firm firm=firmRepository.findById(ferm.getFirmId()).orElse(null);
            //if (firm==null) return false;    
            fermRepository.save(ferm);// insert-update firm
        }
        return true;
    }

    public boolean addFerm(String name, Long firmId){
        if (firmId==null) return false;
        Ferm ferm = new Ferm();
        ferm.setName(name);
        ferm.setFirmId(firmRepository.findById(firmId).orElse(null));
        return addFerm(ferm);
    }

    public boolean addFerm(String name, String firmName){
        if (firmName==null) return false;
        Ferm ferm = new Ferm();
        ferm.setName(name);
        Firm firm=firmRepository.findByShortname(firmName).orElse(null);
        if (firm==null) return false;
        ferm.setFirmId(firm);
        return addFerm(ferm);
    }

    public Ferm getFerm(Long id){
        
        return fermRepository.findById(id).orElse(null);
    }

    public List<Ferm> listFerms(Long firmId){
        Firm f=findById(firmId);
        if (f==null) return null;
        return fermRepository.findByFirmId(f);
    }

   
    public boolean addRealSadok(RealSadok rsadok){
         if (rsadok.getFermId()==null) return false;
         Ferm ferm=fermRepository.findById(rsadok.getFermId().getId()).orElse(null);
         if (ferm==null) return false;    
         realSadokRepository.save(rsadok);// insert-update firm
         return true;
    }

    public boolean addRealSadok(String name, String fermName, String descr){
        if (fermName==null || fermName.equals("")) return false;
        Ferm ferm=fermRepository.findByName(fermName).orElse(null);
        if (ferm==null) return false;
        return addRealSadok(name,ferm.getId(),descr);

    }

    public boolean addRealSadok(String name, Long fermId, String descr){
        if (fermId==null) return false;
        RealSadok rsadok = new RealSadok();
        rsadok.setName(name);
        rsadok.setFermId(fermRepository.findById(fermId).orElse(null));
        rsadok.setDescription(descr);
        return addRealSadok(rsadok);
    }

    public boolean addVirtualSadok(VirtualSadok vsadok){
        if (vsadok.getRSadokId()==null) return false;
        RealSadok rsadok=realSadokRepository.findById(vsadok.getRSadokId().getId()).orElse(null);
        if (rsadok==null) return false;    
        virtualSadokRepository.save(vsadok);// insert-update firm
        return true;
   }

   public boolean addVirtualSadok(String name, Long rsadokId){
       if (rsadokId==null) return false;
       VirtualSadok vsadok = new VirtualSadok();
       vsadok.setName(name);
       vsadok.setRSadokId(realSadokRepository.findById(rsadokId).orElse(null));
       return addVirtualSadok(vsadok);
   }


}
