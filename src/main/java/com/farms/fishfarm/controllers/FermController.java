package com.farms.fishfarm.controllers;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.farms.fishfarm.entities.Ferm;
import com.farms.fishfarm.entities.Firm;
import com.farms.fishfarm.entities.RealSadok;
import com.farms.fishfarm.entities.Role;
import com.farms.fishfarm.entities.User;
import com.farms.fishfarm.services.FermService;
import com.farms.fishfarm.services.FirmService;
import com.farms.fishfarm.services.RoleHelper;
import com.farms.fishfarm.services.UDService;
import com.farms.fishfarm.services.UserHelper;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/ferm")
public class FermController {
    private FirmService firmService;
    private UDService uDService;
    private FermService fermService;
    private User u;
    private Firm fi;
    private Ferm fe;
    private Long fermId;
    private String errorMessage;
    
    private final String redirectRoot="redirect:/ferm";
    private final String redirectUserRoot="redirect:/firmadmin/userlist";
    
    public FermController(FirmService firmService,FermService fermService,UDService uDService) {
        u=null;
        fi=null;
        fe=null;
        errorMessage="";
        this.firmService = firmService;
        this.uDService = uDService;

    }


    private void getUser(){
        if (u!=null) return;
        Authentication au=SecurityContextHolder.getContext().getAuthentication();
		if (au.isAuthenticated())
			{
                u=(User)au.getPrincipal();
			}
		}   
    
    private void getFirm(){
       if (fi!=null) return;
       getUser();
       if (u==null || u.getFirmId()==null) {fi=null;return;}
       fi=firmService.findById(u.getFirmId().getId());
    }

    private boolean isMyFerm(Ferm f){
        getFirm();
        List<Ferm> fl = firmService.listFerms(fi.getId());
        for(Ferm fer:fl){if (fer.getId().equals(f.getId())) return true;}
        return false;
    }

    @GetMapping("{id}")
    public String sadokList(@PathVariable(name = "id") Long id,Model model){
        Ferm ferm = fermService.getFermById(id);
        errorMessage="";
        if (!isMyFerm(ferm)){
            return redirectRoot;
        }
        Set<RealSadok> rsadoks=ferm.getRealSadok();
        model.addAttribute("fermId",ferm.getId());
        model.addAttribute("fermName",ferm.getName());
        model.addAttribute("errorMessage",errorMessage);
        model.addAttribute("rsadiks",rsadoks);

        return "start/sadoklist";
    }



}
