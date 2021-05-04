package com.askrindo.entity;

import com.askrindo.generator.PrefixedSequenceIdGenerator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "mst_project")
@EntityListeners(AuditingEntityListener.class)
public class  Project extends Auditable<String>{

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String projectName;

    private String projectCode;

    private String benefit;
    private String status;

    private Float prosentaseProject;
    private Float budget;
    private Float contractedValue;
    private Float paymentRealization;
    private Integer score;
    private Float weight;

    private String categoryInitiative;
    private String statusProject;
    private String projectDependency;
    private String lineItem;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("project")
//    @JsonIgnore
    private List<Release> releaseList = new ArrayList<>();

    public Project() {
    }

    public Project(String id, String projectName, String projectCode, String benefit, String status, Float prosentaseProject, Float budget, Float contractedValue, Float paymentRealization, Integer score, Float weight, String categoryInitiative, String statusProject, String projectDependency, String lineItem, List<Release> releaseList) {
        this.id = id;
        this.projectName = projectName;
        this.projectCode = projectCode;
        this.benefit = benefit;
        this.status = status;
        this.prosentaseProject = prosentaseProject;
        this.budget = budget;
        this.contractedValue = contractedValue;
        this.paymentRealization = paymentRealization;
        this.score = score;
        this.weight = weight;
        this.categoryInitiative = categoryInitiative;
        this.statusProject = statusProject;
        this.projectDependency = projectDependency;
        this.lineItem = lineItem;
        this.releaseList = releaseList;
    }

    public Project(String projectName, String projectCode, String benefit, String status, Float prosentaseProject, Float budget, Float contractedValue, Float paymentRealization, Integer score, Float weight, String categoryInitiative, String statusProject, String projectDependency, String lineItem, List<Release> releaseList) {
        this.projectName = projectName;
        this.projectCode = projectCode;
        this.benefit = benefit;
        this.status = status;
        this.prosentaseProject = prosentaseProject;
        this.budget = budget;
        this.contractedValue = contractedValue;
        this.paymentRealization = paymentRealization;
        this.score = score;
        this.weight = weight;
        this.categoryInitiative = categoryInitiative;
        this.statusProject = statusProject;
        this.projectDependency = projectDependency;
        this.lineItem = lineItem;
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

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Float getContractedValue() {
        return contractedValue;
    }

    public void setContractedValue(Float contractedValue) {
        this.contractedValue = contractedValue;
    }

    public Float getPaymentRealization() {
        return paymentRealization;
    }

    public void setPaymentRealization(Float paymentRealization) {
        this.paymentRealization = paymentRealization;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getCategoryInitiative() {
        return categoryInitiative;
    }

    public void setCategoryInitiative(String categoryInitiative) {
        this.categoryInitiative = categoryInitiative;
    }

    public String getStatusProject() {
        return statusProject;
    }

    public void setStatusProject(String statusProject) {
        this.statusProject = statusProject;
    }

    public String getProjectDependency() {
        return projectDependency;
    }

    public void setProjectDependency(String keyword) {
        this.projectDependency = keyword;
    }

    public String getLineItem() {
        return lineItem;
    }

    public void setLineItem(String lineItem) {
        this.lineItem = lineItem;
    }

    public List<Release> getReleaseList() {
        return releaseList;
    }

    public void setReleaseList(List<Release> releaseList) {
        this.releaseList = releaseList;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", benefit='" + benefit + '\'' +
                ", status='" + status + '\'' +
                ", prosentaseProject=" + prosentaseProject +
                ", budget=" + budget +
                ", contractedValue=" + contractedValue +
                ", paymentRealization=" + paymentRealization +
                ", score=" + score +
                ", weight=" + weight +
                ", categoryInitiative='" + categoryInitiative + '\'' +
                ", statusProject='" + statusProject + '\'' +
                ", keyword='" + projectDependency + '\'' +
                ", lineItem='" + lineItem + '\'' +
                ", releaseList=" + releaseList +
                '}';
    }
}
