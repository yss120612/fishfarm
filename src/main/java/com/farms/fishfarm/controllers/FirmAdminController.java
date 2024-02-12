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
import com.farms.fishfarm.entities.Role;
import com.farms.fishfarm.entities.User;
import com.farms.fishfarm.services.FirmService;
import com.farms.fishfarm.services.RoleHelper;
import com.farms.fishfarm.services.UDService;
import com.farms.fishfarm.services.UserHelper;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/firmadmin")
public class FirmAdminController {
    private FirmService firmService;
    private UDService uDService;
    private User u;
    private Firm f;
    private String errorMessage;
    
    private final String redirectRoot="redirect:/firmadmin";
    private final String redirectUserRoot="redirect:/firmadmin/userlist";
    
    public FirmAdminController(FirmService firmService,UDService uDService) {
        u=null;
        f=null;
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

    @GetMapping("/userlist")
    public String userList(Model model){
        getFirm();
        if (u!=null) model.addAttribute("user",u.getUsername());
        if (f!=null) 
        {
            model.addAttribute("firmname",f.getShortname());
            model.addAttribute("users",uDService.getAll2firm(f.getId()));
        }
        model.addAttribute("errorMessage",errorMessage);
        return "start/firmuserlist";
    }

    @GetMapping("/adduser")
    public String addUser(Model model){
        getFirm();
        errorMessage="";
        if (u!=null) model.addAttribute("user",u.getUsername());
        if (f!=null) 
        {
        model.addAttribute("firmname",f.getShortname());
        }
        User user=new User();
        user.setFirmId(f);
        //
        model.addAttribute("user",user);
        model.addAttribute("pass1","");
        model.addAttribute("pass2","");
        return "start/addfirmuser";
    }

 

    @GetMapping("/edituser/{id}")
    public String editUser(@PathVariable(name = "id") Long id,Model model){
        getFirm();
        errorMessage="";
        if (u!=null) model.addAttribute("user",u.getUsername());
        if (f!=null) 
        {
        model.addAttribute("firmname",f.getShortname());
        }
        User user=uDService.getUserById(id);
        
        if(u==null||f==null||user==null) return redirectRoot;
        if (!user.getFirmId().getId().equals(f.getId())) 
        {
         errorMessage="Пользователь не найден";   
         return redirectRoot;
        }
        List<Role> roles=uDService.getAllRoles();
        for (Role ro : roles){
            ro.setHasRole(user.getRoles().contains(ro)); 
        }

        model.addAttribute("user",user);
        model.addAttribute("roles",roles);
        model.addAttribute("pass1","");
        model.addAttribute("pass2","");
        return "start/editfirmuser";
    }

    @GetMapping("/deluser/{id}")
    public String delUser(@PathVariable(name = "id") Long id,Model model){
        getFirm();
        errorMessage="";
        if (u!=null) model.addAttribute("user",u.getUsername());
        if (f!=null) 
        {
        model.addAttribute("firmname",f.getShortname());
        }
        User user=uDService.getUserById(id);
        if (user==null){
            errorMessage="Пользователь не найден!";
            return redirectRoot;
        }
        model.addAttribute("user",user);
        return "start/delfirmuser";
    }

    @PostMapping("/saveuser")
    public String saveUser(@ModelAttribute(name = "user") User user){
        User us= uDService.getUserById(user.getId());
        us.setActive(user.getActive());
        us.setEmail(user.getEmail());
        us.setComment(user.getComment());
        uDService.saveUser(us);
        return redirectUserRoot;
    }


    @PostMapping("/savenewuser")
    public String saveNewUser(@ModelAttribute(name = "user") User user,@ModelAttribute(name = "pass1") String  pass1,@ModelAttribute(name = "pass2") String  pass2){
            uDService.addRoleToUser(user, "ROLE_FIRM_WORKER");
            if (pass1==null|| pass1.isEmpty()) {
                errorMessage="Нельзя пустой пароль!";
                return redirectUserRoot;
            }
            if (!pass1.equals(pass2)) {
                errorMessage="Пароли не совпадают!";
                return redirectUserRoot;
            }
        user.setPassword(pass2);    
        uDService.addUser(user);
        return redirectUserRoot;
    }


    @PostMapping("/deluser")
    public String delUserP(@ModelAttribute(name = "user") User user){
        // Logger logger = LoggerFactory.getLogger(SuperAdminController.class);
        // logger.info("ID=%d".formatted(firm.getId()));
        uDService.deleteUser(user.getId());
        return redirectUserRoot;

    }
    @GetMapping("/edituserroles/{id}")
    public String editUserRoles(@PathVariable(name = "id") Long id,Model model){
        getFirm();
        errorMessage="";
        if (u!=null) model.addAttribute("user",u.getUsername());
        if (f!=null) 
        {
        model.addAttribute("firmname",f.getShortname());
        }
        User user=uDService.getUserById(id);
        
        if(u==null||f==null||user==null) return redirectRoot;
        if (!user.getFirmId().getId().equals(f.getId())) 
        {
         errorMessage="Пользователь не найден";   
         return redirectRoot;
        }
        List<Role> roles=uDService.getAllRoles();
        for (Role ro : roles){
            ro.setHasRole(user.getRoles().contains(ro)); 
        }
        RoleHelper rh=new RoleHelper();
        rh.setUserid(user.getId());
        rh.setUsername(user.getUsername());
        rh.setRoles(roles);
        model.addAttribute("rhelper",rh);
        return "start/editfirmuserroles";
    }
    @PostMapping("/saveuserroles")
    public String saveUserRoles(@ModelAttribute(name="rhelper") RoleHelper rhelper){
        User user=uDService.getUserById(rhelper.getUserid());
        Set<Role> ro=new HashSet<>();
         for (Role r:rhelper.getRoles()){
            if(r.isHasRole()) ro.add(r);
         }
        user.setRoles(ro);
        uDService.saveUser(user);
        return redirectUserRoot;
    }


    @GetMapping("/resetpass/{id}")
    public String resetPass(@PathVariable(name = "id") Long id, Model model){
        getFirm();
        errorMessage="";
        if (u!=null) model.addAttribute("user",u.getUsername());
        if (f!=null) 
        {
        model.addAttribute("firmname",f.getShortname());
        }
        User user= uDService.getUserById(id);
        if (user==null) return redirectUserRoot;
        UserHelper userHelper = new UserHelper();

        userHelper.setId(id);
        userHelper.setFirmid(f.getId());
        userHelper.setPass1("");
        userHelper.setPass2("");
        userHelper.setFirmname(f.getShortname());
        userHelper.setUsername(user.getUsername());
        model.addAttribute("uhelper",userHelper);
        return "start/resetpassuser";
    }

    @PostMapping("/resetpass")
    public String resetPassP(@ModelAttribute(name = "uhelper") UserHelper userHelper){
        if (userHelper.getPass1()==null || userHelper.getPass1().isEmpty()){
            errorMessage="Нельзя пустой пароль!";
            return redirectUserRoot;
        }
        if (!userHelper.getPass1().equals(userHelper.getPass2())){
            errorMessage="Пароли не совпадают!";
            return redirectUserRoot;
        }
        if (userHelper.getPass1().length()<4){
            errorMessage="Не менее 4х символов!";
            return redirectUserRoot;
        }
        if (userHelper.getId()!=null && userHelper.getId()>0){
            uDService.resetPassword(userHelper.getUsername(), userHelper.getPass1());
            
        }
        return redirectUserRoot;
    }

}
