package com.farms.fishfarm.entities;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sadok")
public class VirtualSadok {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "vsadok_gen")
    @GenericGenerator(name = "vsadok_gen")
    private Long id;
    @Column(name = "name", length = 256, nullable = false)
    private String name;
    @Column(name = "rSadokId", nullable = false)
    private Long rSadokId;

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

    public Long getRSadokId() {
        return this.rSadokId;
    }

    public void setRSadokId(Long rSadokId) {
        this.rSadokId = rSadokId;
    }

    public VirtualSadok() {
    }

}
