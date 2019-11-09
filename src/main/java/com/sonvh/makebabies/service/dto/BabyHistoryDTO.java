package com.sonvh.makebabies.service.dto;

import com.sonvh.makebabies.domain.BabyHistory;

import java.time.Instant;

public class BabyHistoryDTO {
    private Long id;
    private String img1;
    private String img2;
    private String imgRes;
    private String createdBy;
    private Instant createdDate;
    private String lastModifiedBy;
    private Instant lastModifiedDate;

    public BabyHistoryDTO() {
    }

    public BabyHistoryDTO(BabyHistory babyHistory) {
        this.id = babyHistory.getId();
        this.img1 = babyHistory.getImg1();
        this.img2 = babyHistory.getImg2();
        this.imgRes = babyHistory.getImgRes();
        this.createdBy = babyHistory.getCreatedBy();
        this.createdDate = babyHistory.getCreatedDate();
        this.lastModifiedBy = babyHistory.getLastModifiedBy();
        this.lastModifiedDate = babyHistory.getLastModifiedDate();
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

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImgRes() {
        return imgRes;
    }

    public void setImgRes(String imgRes) {
        this.imgRes = imgRes;
    }
}
