package com.askrindo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "mst_project")
public class Project {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String projectName;
    private String pmo;
    private String pm;
    private String benefit;
    private String description;
    private String coPM;
    private String divisiUser;
    private String directorateUser;
    private String status;

    @JsonFormat(timezone = "Asia/Jakarta",pattern = "yyyy-MM-dd")
    private Date targetLive;
    private Float prosentaseProject;
    private Float budget;
    private Float contracted_value;
    private Float paymentRealization;
    private Integer score;
    private Float weight;
    private String categoryActivity;
    private String categoryInitiative;

    @OneToMany
    @JsonIgnore
    private List<Release> releaseList = new ArrayList<>();

    public Project() {
    }

    public Project(String id, String projectName, String pmo, String pm, String benefit, String description, String coPM, String divisiUser, String directorateUser, String status, Date targetLive, Float prosentaseProject, Float budget, Float contracted_value, Float paymentRealization, Integer score, Float weight, String categoryActivity, String categoryInitiative) {
        this.id = id;
        this.projectName = projectName;
        this.pmo = pmo;
        this.pm = pm;
        this.benefit = benefit;
        this.description = description;
        this.coPM = coPM;
        this.divisiUser = divisiUser;
        this.directorateUser = directorateUser;
        this.status = status;
        this.targetLive = targetLive;
        this.prosentaseProject = prosentaseProject;
        this.budget = budget;
        this.contracted_value = contracted_value;
        this.paymentRealization = paymentRealization;
        this.score = score;
        this.weight = weight;
        this.categoryActivity = categoryActivity;
        this.categoryInitiative = categoryInitiative;
    }

    public Project(String id, String projectName, String pmo, String pm, String benefit, String description, String coPM, String divisiUser, String directorateUser, String status, Date targetLive, Float prosentaseProject, Float budget, Float contracted_value, Float paymentRealization, Integer score, Float weight, String categoryActivity, String categoryInitiative, List<Release> releaseList) {
        this.id = id;
        this.projectName = projectName;
        this.pmo = pmo;
        this.pm = pm;
        this.benefit = benefit;
        this.description = description;
        this.coPM = coPM;
        this.divisiUser = divisiUser;
        this.directorateUser = directorateUser;
        this.status = status;
        this.targetLive = targetLive;
        this.prosentaseProject = prosentaseProject;
        this.budget = budget;
        this.contracted_value = contracted_value;
        this.paymentRealization = paymentRealization;
        this.score = score;
        this.weight = weight;
        this.categoryActivity = categoryActivity;
        this.categoryInitiative = categoryInitiative;
        this.releaseList = releaseList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPmo() {
        return pmo;
    }

    public void setPmo(String pmo) {
        this.pmo = pmo;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoPM() {
        return coPM;
    }

    public void setCoPM(String user) {
        this.coPM = user;
    }

    public String getDivisiUser() {
        return divisiUser;
    }

    public void setDivisiUser(String divisiUser) {
        this.divisiUser = divisiUser;
    }

    public String getDirectorateUser() {
        return directorateUser;
    }

    public void setDirectorateUser(String directorateUser) {
        this.directorateUser = directorateUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTarget_live() {
        return targetLive;
    }

    public void setTarget_live(Date target_live) {
        this.targetLive = target_live;
    }

    public Float getProsentaseProject() {
        return prosentaseProject;
    }

    public void setProsentaseProject(Float prosentaseProject) {
        this.prosentaseProject = prosentaseProject;
    }

    public Float getBudget() {
        return budget;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    public Float getContracted_value() {
        return contracted_value;
    }

    public void setContracted_value(Float contracted_value) {
        this.contracted_value = contracted_value;
    }

    public Float getPaymentRealization() {
        return paymentRealization;
    }

    public void setPaymentRealization(Float paymentRealization) {
        this.paymentRealization = paymentRealization;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getCategoryActivity() {
        return categoryActivity;
    }

    public void setCategoryActivity(String categoryActivity) {
        this.categoryActivity = categoryActivity;
    }

    public String getCategoryInitiative() {
        return categoryInitiative;
    }

    public void setCategoryInitiative(String categoryInitiative) {
        this.categoryInitiative = categoryInitiative;
    }

    public Date getTargetLive() {
        return targetLive;
    }

    public void setTargetLive(Date targetLive) {
        this.targetLive = targetLive;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<Release> getReleaseList() {
        return releaseList;
    }

    public void setReleaseList(List<Release> releaseList) {
        this.releaseList = releaseList;
    }
}
