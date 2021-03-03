package com.askrindo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "mst_division")
public class Division {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String divisionName;
    private String divisionCode;

    @OneToMany
    @JsonIgnore
    private List<Project> projectList = new ArrayList<>();

    public Division(String id, String divisionName, String divisionCode) {
        this.id = id;
        this.divisionName = divisionName;
        this.divisionCode = divisionCode;
    }

    public Division(String id, String divisionName, String divisionCode, List<Project> projectList) {
        this.id = id;
        this.divisionName = divisionName;
        this.divisionCode = divisionCode;
        this.projectList = projectList;
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

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }
}
