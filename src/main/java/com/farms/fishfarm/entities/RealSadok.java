package com.farms.fishfarm.entities;

import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="realsadok")
public class RealSadok {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="rsadok_gen")
    @GenericGenerator(name="rsadok_gen")
    private Long id;
    @Column(name="name",length=256,nullable = false)
    private String name;
    @Column(name="description",length=256)
    private String description;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "rSadokId")
    private Set<VirtualSadok> vsadoks;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<VirtualSadok> getVsadoks() {
        return this.vsadoks;
    }

    public void setVsadoks(Set<VirtualSadok> vsadoks) {
        this.vsadoks = vsadoks;
    }

    public RealSadok() {
    }

}
