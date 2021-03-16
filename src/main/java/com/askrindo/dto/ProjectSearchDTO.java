package com.askrindo.dto;

import com.askrindo.entity.Division;
import com.askrindo.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ProjectSearchDTO {

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division divisiUser;
    private String catAct;
    private String pm;
    private String pmo;
    private String copm;
    private String status;

    public ProjectSearchDTO(Division divisiUser, String catAct, String pm, String pmo, String copm, String status) {
        this.divisiUser = divisiUser;
        this.catAct = catAct;
        this.pm = pm;
        this.pmo = pmo;
        this.copm = copm;
        this.status = status;
    }

    public Division getDivisiUser() {
        return divisiUser;
    }

    public void setDivisiUser(Division divisiUser) {
        this.divisiUser = divisiUser;
    }

    public String getCatAct() {
        return catAct;
    }

    public void setCatAct(String catAct) {
        this.catAct = catAct;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getPmo() {
        return pmo;
    }

    public void setPmo(String pmo) {
        this.pmo = pmo;
    }

    public String getCopm() {
        return copm;
    }

    public void setCopm(String copm) {
        this.copm = copm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProjectSearchDTO() {

    }
}