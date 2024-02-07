package com.farms.fishfarm.controllers;

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

    public FirmAdminController(FirmService firmService) {
        this.firmService = firmService;
    }

    private User getUser(){
        Authentication au=SecurityContextHolder.getContext().getAuthentication();
		if (au.isAuthenticated())
			{
				return (User)au.getPrincipal();
			}else{
				return null;
			}
		}   
    
    private Firm getFirm(){
       User u=getUser();
       if (u==null || u.getFirmId()==null) return null;
       return firmService.findById(u.getFirmId().getId());
    }

    @GetMapping("")
    public String main(Model model){
        User u=getUser();
        Firm f=getFirm();
        if (u!=null) model.addAttribute("user",u.getUsername());
        if (f!=null) 
        {
        model.addAttribute("firmname",f.getShortname());
        model.addAttribute("ferms",firmService.listFerms(f.getId()));
        }
        return "start/firmadmin";
    }

    @GetMapping("/addferm")
    public String addFerm(Model model){
        User u=getUser();
        Firm f=getFirm();
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

    @GetMapping("/editferm/{id}")
    public String editFerm(@PathVariable(name = "id") Long id,Model model){
        User u=getUser();
        Firm f=getFirm();
        if (u!=null) model.addAttribute("user",u.getUsername());
        if (f!=null) 
        {
        model.addAttribute("firmname",f.getShortname());
        }
        Ferm ferm=firmService.getFerm(id);
        if(u==null||f==null||ferm==null) return "redirect:/fermadmin";
        if (!ferm.getFirmId().getId().equals(f.getId())) return "redirect:/fermadmin";
        model.addAttribute("ferm",ferm);
        return "start/editferm";
    }


    @PostMapping("/saveferm")
    public String saveFerm(@ModelAttribute(name = "ferm") Ferm ferm){
        // Logger logger = LoggerFactory.getLogger(SuperAdminController.class);
        // logger.info("ID=%d".formatted(firm.getId()));
        firmService.addFerm(ferm);
        return "redirect:/firmadmin";
    }

}
