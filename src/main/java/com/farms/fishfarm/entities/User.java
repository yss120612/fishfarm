package com.farms.fishfarm.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;


@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="user_gen")
    @GenericGenerator(name="user_gen")
    private Long id;
    @Column(name="login",unique = true,length=64,nullable = false)
    private String username;

    @Column(name="password",length=64,nullable = false)
    private String password;

    @Column(name="email",length=128)
    private String email;

    @Column(name="firmId")
    private Long firmId;

    @Column(name="active")
    private boolean active;

    @Column(name="descr", length = 1024)
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isEnabled() {
        return active;
    }

    public void setEnabled(boolean active) {
        this.active = active;
    }

    public User() {
      // TODO document why this constructor is empty
    }


    public Long getFirm() {
        return this.firmId;
    }

    public void setFirm(Long firm) {
        this.firmId = firm;
    }

    
    public Set<Role> getRoles() {
        return this.roles;
    }



//    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//    @Enumerated( EnumType.ORDINAL)
//    @CollectionTable(name="user_roles", joinColumns = @JoinColumn(name="user_id"))
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
        
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
    return true;
    }
}
