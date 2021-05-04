package com.askrindo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "mst_division")
@EntityListeners(AuditingEntityListener.class)
public class Division extends Auditable<String>{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String divisionName;
    private String divisionCode;

    @OneToMany
    @JsonIgnore
    private List<Release> releaseList = new ArrayList<>();

    public Division(String id, String divisionName, String divisionCode) {
        this.id = id;
        this.divisionName = divisionName;
        this.divisionCode = divisionCode;
    }

    public Division(String id, String divisionName, String divisionCode, List<Release> releaseList) {
        this.id = id;
        this.divisionName = divisionName;
        this.divisionCode = divisionCode;
        this.releaseList = releaseList;
    }

    public Division() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    public List<Release> getReleaseList() {
        return releaseList;
    }

    public void setReleaseList(List<Release> releaseList) {
        this.releaseList = releaseList;
    }

    @Override
    public String toString() {
        return "Division{" +
                "id='" + id + '\'' +
                ", divisionName='" + divisionName + '\'' +
                ", divisionCode='" + divisionCode + '\'' +
                ", releaseList=" + releaseList +
                '}';
    }
}
