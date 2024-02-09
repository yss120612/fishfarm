package com.farms.fishfarm.controllers;


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
import com.farms.fishfarm.entities.User;
import com.farms.fishfarm.services.FirmService;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/firmadmin")
public class FirmAdminController {
    private FirmService firmService;
    private User u;
    private Firm f;
    private String errorMessage;
    private final String redirectRoot="redirect:/firmadmin";
    public FirmAdminController(FirmService firmService) {
        u=null;
        f=null;
        errorMessage="";
        this.firmService = firmService;

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
       if (f!=null) return;
       getUser();
       if (u==null || u.getFirmId()==null) {f=null;return;}
       f=firmService.findById(u.getFirmId().getId());
    }

    @GetMapping("")
    public String main(Model model){
        getFirm();
        if (u!=null) model.addAttribute("user",u.getUsername());
        if (f!=null) 
        {
            model.addAttribute("firmname",f.getShortname());
            model.addAttribute("ferms",firmService.listFerms(f.getId()));
        }
        model.addAttribute("errorMessage",errorMessage);
        return "start/firmadmin";
    }
    
    @GetMapping("/addferm")
    public String addFerm(Model model){
        getFirm();
        errorMessage="";
        if (u!=null) model.addAttribute("user",u.getUsername());
        if (f!=null) 
        {
        model.addAttribute("firmname",f.getShortname());
        }
        Ferm ferm=new Ferm();
        ferm.setFirmId(f);
        model.addAttribute("ferm",ferm);
        return "start/addferm";
    }


    @GetMapping("/delferm/{id}")
    public String delFerm(@PathVariable(name = "id") Long id,Model model){
        getFirm();
        errorMessage="";
        if (u!=null) model.addAttribute("user",u.getUsername());
        if (f!=null) 
        {
        model.addAttribute("firmname",f.getShortname());
        }
        Ferm ferm=firmService.getFerm(id);
        if (!ferm.getRealSadok().isEmpty()){
            errorMessage="Ферма имеет садки. Удалить нелзя!";
            return redirectRoot;
        }
        model.addAttribute("ferm",ferm);
        return "start/delferm";
    }

    @GetMapping("/editferm/{id}")
    public String editFerm(@PathVariable(name = "id") Long id,Model model){
        getFirm();
        errorMessage="";
        if (u!=null) model.addAttribute("user",u.getUsername());
        if (f!=null) 
        {
        model.addAttribute("firmname",f.getShortname());
        }
        Ferm ferm=firmService.getFerm(id);
        //  Logger logger = LoggerFactory.getLogger(FirmAdminController.class);
        //  logger.info("NAME=%s".formatted(f.getShortname()));
        //  logger.info("NAME=%s".formatted(ferm.getName()));
        
        if(u==null||f==null||ferm==null) return redirectRoot;
        if (!ferm.getFirmId().getId().equals(f.getId())) 
        {
         errorMessage="Ферма не найдена";   
         return redirectRoot;
        }
        model.addAttribute("ferm",ferm);
        return "start/editferm";
    }


    @PostMapping("/saveferm")
    public String saveFerm(@ModelAttribute(name = "ferm") Ferm ferm){
        // Logger logger = LoggerFactory.getLogger(SuperAdminController.class);
        // logger.info("ID=%d".formatted(firm.getId()));
        firmService.addFerm(ferm);
        return redirectRoot;
    }

    @PostMapping("/delferm")
    public String delFermP(@ModelAttribute(name = "ferm") Ferm ferm){
        // Logger logger = LoggerFactory.getLogger(SuperAdminController.class);
        // logger.info("ID=%d".formatted(firm.getId()));
        firmService.deleteFerm(ferm);
        return redirectRoot;

    }

}
