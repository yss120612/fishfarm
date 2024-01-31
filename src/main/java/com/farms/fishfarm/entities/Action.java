package com.farms.fishfarm.entities;

import org.hibernate.annotations.GenericGenerator;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="actions")
public class Action {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="action_gen")
    @GenericGenerator(name="action_gen")
    private Long id;
    @Column(name="name",unique = true,length=256,nullable = false)
    private String name;


    public Action() {
      // TODO document why this constructor is empty
    }


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
    
}
