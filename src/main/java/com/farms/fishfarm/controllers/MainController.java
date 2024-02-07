package com.farms.fishfarm.controllers;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Marker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.farms.fishfarm.entities.Role;
import com.farms.fishfarm.entities.User;
import com.farms.fishfarm.repo.RoleRepository;
import com.farms.fishfarm.services.FirmService;
import com.farms.fishfarm.services.UDService;

@Controller
public class MainController {

	public User currentUser(){
			Authentication au=SecurityContextHolder.getContext().getAuthentication();
			if (au.isAuthenticated())
			{
				return (User)au.getPrincipal();
			}else{
				return null;
			}
		}


    private UDService uDService;
    private FirmService firmService;
    //private User currentUser;

    public MainController(UDService uDService, FirmService firmService) {
        this.uDService = uDService;
        this.firmService = firmService;
      //  this.currentUser = currentUser;
    }

   
    

    @GetMapping("/main")
    public String main(Model model){
        if (currentUser()!=null)
        {
        model.addAttribute("user",currentUser().getUsername());
        }
        else{
            model.addAttribute("user","Anonimous");
        }
        return "start/main";
    }

    @GetMapping("/home")
    public String home(){
        return "start/home";
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
          // logger.info("add firm 1=%b".formatted(firmService.addFirm("Fishes Lake", "Fishes Like incorporated","7711122233","Rio de Janeiro, Lenin street, 1")));
          // logger.info("add firm 2=%b".formatted(firmService.addFirm("Fishes Sea", "Fishes Sea Ltd.","3333333331","Selo , 1 Avenu, 1")));

          // logger.info("addUser superadmin =%b".formatted(uDService.addUser("superadmin","1111", 0L)));  
          // logger.info("addUser firmadmin =%b".formatted(uDService.addUser("firmadmin","1111", "Fishes Lake")));  
          // logger.info("addUser worker =%b".formatted(uDService.addUser("worker","1111", "Fishes Lake")));  

          // logger.info("addRole ROLE_SUPER_ADMIN =%b".formatted(uDService.addRole("ROLE_SUPER_ADMIN")));  
          // logger.info("addRole ROLE_FIRM_ADMIN =%b".formatted(uDService.addRole("ROLE_FIRM_ADMIN")));  

          

          // logger.info("add role ROLE_SUPER_ADMIN to user superadmin =%b".formatted(uDService.addRoleToUserByName("superadmin", "ROLE_SUPER_ADMIN")));
          // logger.info("add role ROLE_FIRM_ADMIN to user fermradmin =%b".formatted(uDService.addRoleToUserByName("firmadmin", "ROLE_FIRM_ADMIN")));

          // logger.info("change password superadmin ="+ uDService.changePassword("superadmin", "1111", "2222"));
          // logger.info("change password worker = "+ uDService.changePassword("worker", "1111", "3333"));

          // logger.info("add ferm 1=%b".formatted(firmService.addFerm("Ferm one","Fishes Lake")));
          // logger.info("add ferm 2=%b".formatted(firmService.addFerm("Ferm two","Fishes Sea")));
          // logger.info("add ferm 3=%b".formatted(firmService.addFerm("Ferm three","Fishes Lake")));
          // logger.info("add ferm 4=%b".formatted(firmService.addFerm("Ferm four","Fishes Sea")));
          
        return "start/main";
    }

}