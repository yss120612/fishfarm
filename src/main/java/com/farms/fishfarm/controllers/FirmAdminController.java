package com.farms.fishfarm.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.farms.fishfarm.entities.User;

@Controller
public class FirmAdminController {
    
    @GetMapping("/firmadmin")
    public String main(Model model){
        if (SecurityContextHolder.getContext().getAuthentication()!=null)
        {
            model.addAttribute("user",((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        }
        else{
            model.addAttribute("user","Anonimous");
        }
        return "start/firmadmin";
    }
}
