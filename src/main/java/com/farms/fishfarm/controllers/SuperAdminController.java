package com.farms.fishfarm.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.farms.fishfarm.entities.Firm;
import com.farms.fishfarm.entities.User;
import com.farms.fishfarm.services.UDService;
import com.farms.fishfarm.services.UserHelper;
import com.farms.fishfarm.services.FirmService;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/superadmin")
public class SuperAdminController {

UDService uDService;
FirmService firmService;

public SuperAdminController(UDService uDService, FirmService firmService) {
        this.uDService = uDService;
        this.firmService = firmService;
    }

    @GetMapping("")
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

    @GetMapping("/addfirm")
    public String addFirm(Model model){
        model.addAttribute("firm",new Firm());
        return "start/addfirm";
    }

    @GetMapping("/editfirm/{id}")
    public String editFirm(@PathVariable(name = "id") Long id, Model model){
        Firm firm= firmService.findById(id);
        if (firm==null) return "/";
        model.addAttribute("firm",firm);
        return "start/editfirm";
    }

    @GetMapping("/delfirm/{id}")
    public String delFirmG(@PathVariable(name = "id") Long id, Model model){
        Firm firm= firmService.findById(id);
        if (firm==null) return "/";
        model.addAttribute("firm",firm);
        return "start/delfirm";
    }

    @PostMapping("/savefirm")
    public String saveFirm(@ModelAttribute(name = "firm") Firm firm){
        // Logger logger = LoggerFactory.getLogger(SuperAdminController.class);
        // logger.info("ID=%d".formatted(firm.getId()));
        firmService.addFirm(firm);
        return "redirect:/superadmin";
    }

    @PostMapping("/delfirm")
    public String delFirmP(@ModelAttribute(name = "firm") Firm firm){
        // Logger logger = LoggerFactory.getLogger(SuperAdminController.class);
        // logger.info("ID=%d".formatted(firm.getId()));
        firmService.deleteFirm(firm);
        return "redirect:/superadmin";
    }

    @GetMapping("/adminslist/{id}")
    public String adminsList(@PathVariable(name = "id") Long id, Model model){
        List<User> users=  uDService.getAll2firm(id);
        model.addAttribute("admins",users);
        model.addAttribute("firmid",id);
        Firm firm=firmService.findById(id);
        if (firm!=null) model.addAttribute("firmname",firm.getShortname());
        return "start/adminlist";
    }

    @GetMapping("/addadmin/{id}")
    public String addAdmin(@PathVariable(name = "id") Long id, Model model){
        Firm firm= firmService.findById(id);
        if (firm==null) return "/";
        UserHelper userHelper = new UserHelper();
        userHelper.setId(null);
        userHelper.setFirmid(id);
        userHelper.setPass1("");
        userHelper.setPass2("");
        userHelper.setFirmname(firm.getShortname());
        userHelper.setUsername("");
        model.addAttribute("admin",userHelper);
        return "start/addadmin";
    }


    @GetMapping("/editadmin/{idfirm}/{id}")
    public String addAdmin(@PathVariable(name = "idfirm") Long idfirm, @PathVariable(name = "id") Long id, Model model){
        Firm firm= firmService.findById(idfirm);
        User user= uDService.getUserById(id);
        if (firm==null || user==null) return "redirect:/superadmin/adminslist/%d".formatted(idfirm);
        UserHelper userHelper = new UserHelper();

        userHelper.setId(id);
        userHelper.setFirmid(idfirm);
        userHelper.setPass1("");
        userHelper.setPass2("");
        userHelper.setFirmname(firm.getShortname());
        userHelper.setUsername(user.getUsername());
        model.addAttribute("admin",userHelper);
        return "start/editadmin";
    }


    @GetMapping("/deleteadmin/{idfirm}/{id}")
    public String deleteAdmin(@PathVariable(name = "idfirm") Long idfirm, @PathVariable(name = "id") Long id, Model model){
        Firm firm= firmService.findById(idfirm);
        User user= uDService.getUserById(id);
        if (firm==null || user==null) return "redirect:/superadmin/adminslist/%d".formatted(idfirm);
        UserHelper userHelper = new UserHelper();

        userHelper.setId(id);
        userHelper.setFirmid(idfirm);
        userHelper.setPass1("");
        userHelper.setPass2("");
        userHelper.setFirmname(firm.getShortname());
        userHelper.setUsername(user.getUsername());
        model.addAttribute("admin",userHelper);
        return "start/deleteadmin";
    }

    @PostMapping("/saveadmin")
    public String saveAdmin(@ModelAttribute(name = "admin") UserHelper admin){
        if (admin.getPass1()==null || admin.getPass1().isEmpty() || !admin.getPass1().equals(admin.getPass2())){
            return "redirect:/adminlist/%d".formatted(admin.getFirmid());    
        }
        // Logger logger = LoggerFactory.getLogger(SuperAdminController.class);
        // logger.info("username=%s password=%S".formatted(admin.getUsername(),admin.getPass1()));

        if (admin.getId()!=null && admin.getId()>0){
            uDService.resetPassword(admin.getUsername(), admin.getPass1());
            
        }else{
            if (uDService.addUser(admin.getUsername(), admin.getPass1(), admin.getFirmid())){
                uDService.addRoleToUserByName(admin.getUsername(),"ROLE_FIRM_ADMIN");
            }
        }
        return "redirect:/superadmin/adminslist/%d".formatted(admin.getFirmid());
    }

    @PostMapping("/deleteadmin")
    public String deleteAdminP(@ModelAttribute(name = "admin") UserHelper admin){
        // Logger logger = LoggerFactory.getLogger(SuperAdminController.class);
        // logger.info("ID=%d".formatted(firm.getId()));
        uDService.deleteUserById(admin.getId());
        return "redirect:/superadmin/adminslist/%d".formatted(admin.getFirmid());
    }


}
