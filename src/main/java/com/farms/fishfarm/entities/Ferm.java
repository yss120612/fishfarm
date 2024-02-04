package com.farms.fishfarm.entities;

import jakarta.persistence.*;

import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="ferms")
public class Ferm {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="ferm_gen")
    @GenericGenerator(name="ferm_gen")
    private Long id;
    @Column(name="name",unique = true,length=128,nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="firm_id", nullable=false)
    private Firm firmId;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "fermId")
    //@JoinColumn(name = "realSadok")
    private Set<RealSadok> realSadok;


    public Set<RealSadok> getRealSadok() {
        return this.realSadok;
    }

    public void setRealSadok(Set<RealSadok> realSadok) {
        this.realSadok = realSadok;
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

    

    public Firm getFirmId() {
        return this.firmId;
    }

    public void setFirmId(Firm firmId) {
        this.firmId = firmId;
    }

    public Ferm() {
      // TODO document why this constructor is empty
    }
}
