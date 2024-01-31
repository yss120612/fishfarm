package com.farms.fishfarm.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="roles_gen")
    @GenericGenerator(name="roles_gen")
    private Long id;

    @Column(name="name",unique = true, length = 128)
    private String name;
    
    @Column(name="descr", length = 1024)
    private String comment;

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

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    
    public Role() {
    }

    @Override
    public String getAuthority() {
        return name;
    }



}
