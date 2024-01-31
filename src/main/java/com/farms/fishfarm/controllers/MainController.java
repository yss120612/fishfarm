package com.farms.fishfarm.controllers;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Marker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.farms.fishfarm.entities.Role;
import com.farms.fishfarm.entities.User;
import com.farms.fishfarm.repo.RoleRepository;
import com.farms.fishfarm.services.FirmService;
import com.farms.fishfarm.services.UDService;

@Controller
public class MainController {


    private UDService uDService;
    private FirmService firmService;
    

    public MainController(UDService uDService, FirmService firmService) {
        this.uDService = uDService;
        this.firmService = firmService;
    }

    

    @GetMapping("/main")
    public String main(){
        return "main";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/")
    public String root(){
          Logger logger = LoggerFactory.getLogger(MainController.class);
        //   User us=new User();    
        //   us.setUsername("superadmin");
        //   us.setPassword("1111");
        //   us.setId(0L);
        //   logger.trace(us.getUsername()+" "+us.getPassword());
          //uDService.addUser(us);    
          
          logger.info("addRole SUPER_ADMIN =%b".formatted(uDService.addRole("SUPER_ADMIN")));  
          logger.info("addRole FIRM_ADMIN =%b".formatted(uDService.addRole("FIRM_ADMIN")));  
          logger.info("addUser super_admin =%b".formatted(uDService.addUser("superadmin", "1111", null)));
          logger.info("add role SUPER_ADMIN to user superadmin =%b".formatted(uDService.addRoleToUserByName("superadmin", "SUPER_ADMIN")));
          logger.info("change password ="+ uDService.changePassword("superadmin", "1111", "2222"));
          logger.info("add firm =%b".formatted(firmService.addFirm("Fishes Lake", "Fishes Like incorporated","7711122233","Rio de Janeiro, Lenin street, 1")));

          
        return "main";
    }

}