package com.sonvh.makebabies.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "baby_history")
public class BabyHistory extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_mom")
    private String imgMom;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "baby_history_id")
    @JsonManagedReference
    private Set<DadAndSon> dadAndSons;

    @Column(name = "name")
    private String babyname;

    @Column(name = "gender")
    private String gender;

    @Column(name= "ethnicity")
    private String ethnicity;

    public String getBabyname() {
        return babyname;
    }

    public void setBabyname(String babyname) {
        this.babyname = babyname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgMom() {
        return imgMom;
    }

    public void setImgMom(String imgMom) {
        this.imgMom = imgMom;
    }

    public Set<DadAndSon> getDadAndSons() {
        return dadAndSons;
    }

    public void setDadAndSons(Set<DadAndSon> dadAndSons) {
        this.dadAndSons = dadAndSons;
    }
}
