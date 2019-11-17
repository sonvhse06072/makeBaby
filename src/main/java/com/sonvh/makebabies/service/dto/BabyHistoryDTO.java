package com.sonvh.makebabies.service.dto;

import com.sonvh.makebabies.domain.BabyHistory;
import com.sonvh.makebabies.domain.DadAndSon;

import java.time.Instant;
import java.util.Set;

public class BabyHistoryDTO {
    private Long id;
    private String imgMom;
    private String gender;
    private String ethnicity;
    private String babyname;
    private Set<DadAndSon> dadAndSons;
    private String createdBy;
    private Instant createdDate;
    private String lastModifiedBy;
    private Instant lastModifiedDate;

    public BabyHistoryDTO() {
    }

    public BabyHistoryDTO(BabyHistory babyHistory) {
        this.id = babyHistory.getId();
        this.imgMom = babyHistory.getImgMom();
        this.gender = babyHistory.getGender();
        this.ethnicity = babyHistory.getEthnicity();
        this.babyname = babyHistory.getBabyname();
        this.dadAndSons = babyHistory.getDadAndSons();
        this.createdBy = babyHistory.getCreatedBy();
        this.createdDate = babyHistory.getCreatedDate();
        this.lastModifiedBy = babyHistory.getLastModifiedBy();
        this.lastModifiedDate = babyHistory.getLastModifiedDate();
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

    public String getBabyname() {
        return babyname;
    }

    public void setBabyname(String babyname) {
        this.babyname = babyname;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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
