package com.farms.fishfarm.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.farms.fishfarm.entities.User;
import com.farms.fishfarm.repo.RoleRepository;

import com.farms.fishfarm.services.UDService;

@Controller
public class LoginController {

    private UDService uDService;
    private RoleRepository roleRepository;

    public LoginController(UDService uDService, RoleRepository roleRepository) {
        this.uDService = uDService;
        this.roleRepository = roleRepository;
    }

    // @GetMapping("/login")
    // public String login(){
    // User us=new User();    
    // us.setUsername("superadmin");
    // us.setPassword("1111");
    // Set<Role> rhs=new HashSet<>();
    // Role r=roleRepository.findById(1L).get();
    // rhs.add(r);                        
    // us.setRoles(rhs);
    // uDService.addUser(us);    
    //     return "login";
    // }

    @GetMapping("/login")
	public String loginG(Model model,@RequestParam(value="error",required=false) String error,@RequestParam(value="logout",required=false) String logout) {
		if (error!=null)
		{
			model.addAttribute("error","Имя или пароль не верны!");
			model.addAttribute("username","");
			model.addAttribute("password","");
		}
		if (logout!=null)
		{
			model.addAttribute("messa","Выход успешно осуществлен!");
			model.addAttribute("username","");
			model.addAttribute("password","");
		}
		return "login";
	}

    @PostMapping("/login")
	public String loginP(Model model,@RequestParam(value="username",required=true) String un,@RequestParam(value="password",required=true) String pass) {
		
		return "/home";
	}



    @GetMapping("/chgpwd")
	public String chgpwdGET(Model model) {
        User u=getUser();
		
		if (u!=null)
		{
			model.addAttribute("name", u.getUsername());
            return "chgpass";
		}
		return "login";
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
	
	
	@PostMapping("/chgpwd")
	public String chgpwdPOST(Model model,
							@RequestParam(value="password_old",required=true) String lpo,
							@RequestParam(value="password_new",required=true) String lpn,
							@RequestParam(value="password_new2",required=true) String lpn2) {
		User u=getUser();
		String result="";
		        
		if (u!=null)
		{
			result=uDService.changePassword(u.getUsername(), lpo, lpn);	
		}
		
		//model.addAttribute("name", u.getUsername());
		//model.addAttribute("apage","home");
		
		
		
		if (result.isEmpty())
		{
			model.addAttribute("rest","Пароль успешно сменен");
			
		}
		else {
			model.addAttribute("err",result);
		}
		return "start";
	}


}
