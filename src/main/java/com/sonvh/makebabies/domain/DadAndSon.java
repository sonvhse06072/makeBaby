package com.sonvh.makebabies.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "dad_and_son")
public class DadAndSon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_dad")
    private String imgDad;

    @Column(name = "image_son")
    private String imgSon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private BabyHistory babyHistory;

    public BabyHistory getBabyHistory() {
        return babyHistory;
    }

    public void setBabyHistory(BabyHistory babyHistory) {
        this.babyHistory = babyHistory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgDad() {
        return imgDad;
    }

    public void setImgDad(String imgDad) {
        this.imgDad = imgDad;
    }

    public String getImgSon() {
        return imgSon;
    }

    public void setImgSon(String imgSon) {
        this.imgSon = imgSon;
    }
}
