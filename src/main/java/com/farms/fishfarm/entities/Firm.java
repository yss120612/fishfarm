package com.farms.fishfarm.entities;

import jakarta.persistence.*;

import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="organizations")
public class Firm {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="firm_gen")
    @GenericGenerator(name="firm_gen")
    private Long id;
    @Column(name="shortname",unique = true,length=128,nullable = false)
    private String shortname;
    @Column(name="inn",length=12,nullable = true)
    private String inn;
    @Column(name="fullname",length=1024,nullable = true)
    private String fullname;
    @Column(name="address",length=256,nullable = true)
    private String address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "firmId")
    private Set<User> users;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "firmId")
    private Set<Ferm> ferms;
    
    public Firm() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Ferm> getFerms() {
        return this.ferms;
    }

    public void setFerms(Set<Ferm> ferms) {
        this.ferms = ferms;
    }
}

    
