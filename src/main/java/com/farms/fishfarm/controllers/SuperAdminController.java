package com.farms.fishfarm.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.farms.fishfarm.entities.User;
import com.farms.fishfarm.services.UDService;
import com.farms.fishfarm.services.FirmService;

@Controller
public class SuperAdminController {

UDService uDService;
FirmService firmService;

   

    public SuperAdminController(UDService uDService, FirmService firmService) {
        this.uDService = uDService;
        this.firmService = firmService;
    }

    @GetMapping("/superadmin")
    public String main(Model model){
        if (SecurityContextHolder.getContext().getAuthentication()!=null)
        {
            model.addAttribute("user",((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        }
        else{
            model.addAttribute("user","Anonimous");
        }
        
        model.addAttribute("firms",firmService.getFirms());

        return "start/superadmin";
    }
}
