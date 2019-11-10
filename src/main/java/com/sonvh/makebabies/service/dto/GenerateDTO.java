package com.sonvh.makebabies.service.dto;

import com.sonvh.makebabies.domain.BabyHistory;
import org.apache.http.entity.mime.content.ContentBody;

public class GenerateDTO {
    String img1;
    String img2;
    String gender;
    String ethnicity;
    String babyname;

    public GenerateDTO() {
    }

    public GenerateDTO(String img1, String img2, String gender, String ethnicity, String babyname) {
        this.img1 = img1;
        this.img2 = img2;
        this.gender = gender;
        this.ethnicity = ethnicity;
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

    public String getBabyname() {
        return babyname;
    }

    public void setBabyname(String babyname) {
        this.babyname = babyname;
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
}
