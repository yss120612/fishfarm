package com.farms.fishfarm.services;

import org.springframework.stereotype.Service;

@Service
public class UserHelper {
    private Long id;
    private Long firmid;
    private String pass1;
    private String pass2;
    private String firmname;
    private String username;

    public UserHelper() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFirmid() {
        return this.firmid;
    }

    public void setFirmid(Long firmid) {
        this.firmid = firmid;
    }

    public String getPass1() {
        return this.pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

    public String getPass2() {
        return this.pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }

    public String getFirmname() {
        return this.firmname;
    }

    public void setFirmname(String firmname) {
        this.firmname = firmname;
    }
    
}

