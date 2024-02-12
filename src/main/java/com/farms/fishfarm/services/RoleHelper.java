package com.farms.fishfarm.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.farms.fishfarm.entities.Role;

@Service
public class RoleHelper {
    private Long userid;
    private String username;
    private List<Role> roles;

    public RoleHelper() {
    }
    
   public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}