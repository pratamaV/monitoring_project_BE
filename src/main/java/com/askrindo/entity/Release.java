package com.askrindo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "mst_release")
public class Release {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String releaseName;
    private Integer score;
    private Float weight;
    private String description;
    private String status;
    private String stage;
    private Float prosentaseRelease;

    @JsonFormat(timezone = "Asia/Jakarta",pattern = "yyyy-MM-dd")
    private Date estStartdate;

    @JsonFormat(timezone = "Asia/Jakarta",pattern = "yyyy-MM-dd")
    private Date estEnddate;

    @JsonFormat(timezone = "Asia/Jakarta",pattern = "yyyy-MM-dd")
    private Date actStartdate;

    @JsonFormat(timezone = "Asia/Jakarta",pattern = "yyyy-MM-dd")
    private Date actEnddate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany
    @JsonIgnore
    private List<Task> taskList = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    private List<Issued> issuedList = new ArrayList<>();

    public Release() {
    }

    public Release(String id, String releaseName, Integer score, Float weight, String description, String status, String stage, Float prosentaseRelease, Date estStartdate, Date estEnddate, Date actStartdate, Date actEnddate, Project project, List<Task> taskList, List<Issued> issuedList) {
        this.id = id;
        this.releaseName = releaseName;
        this.score = score;
        this.weight = weight;
        this.description = description;
        this.status = status;
        this.stage = stage;
        this.prosentaseRelease = prosentaseRelease;
        this.estStartdate = estStartdate;
        this.estEnddate = estEnddate;
        this.actStartdate = actStartdate;
        this.actEnddate = actEnddate;
        this.project = project;
        this.taskList = taskList;
        this.issuedList = issuedList;
    }

    public Release(String releaseName, Integer score, Float weight, String description, String status, String stage, Float prosentaseRelease, Date estStartdate, Date estEnddate, Date actStartdate, Date actEnddate, Project project, List<Task> taskList, List<Issued> issuedList) {
        this.releaseName = releaseName;
        this.score = score;
        this.weight = weight;
        this.description = description;
        this.status = status;
        this.stage = stage;
        this.prosentaseRelease = prosentaseRelease;
        this.estStartdate = estStartdate;
        this.estEnddate = estEnddate;
        this.actStartdate = actStartdate;
        this.actEnddate = actEnddate;
        this.project = project;
        this.taskList = taskList;
        this.issuedList = issuedList;
    }

    public Release(String releaseName, Integer score, Float weight, String description, String status, String stage, Float prosentaseRelease, Date estStartdate, Date estEnddate, Date actStartdate, Date actEnddate, Project project) {
        this.releaseName = releaseName;
        this.score = score;
        this.weight = weight;
        this.description = description;
        this.status = status;
        this.stage = stage;
        this.prosentaseRelease = prosentaseRelease;
        this.estStartdate = estStartdate;
        this.estEnddate = estEnddate;
        this.actStartdate = actStartdate;
        this.actEnddate = actEnddate;
        this.project = project;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Float getProsentaseRelease() {
        return prosentaseRelease;
    }

    public void setProsentaseRelease(Float prosentaseRelease) {
        this.prosentaseRelease = prosentaseRelease;
    }

    public Date getEstStartdate() {
        return estStartdate;
    }

    public void setEstStartdate(Date estStartdate) {
        this.estStartdate = estStartdate;
    }

    public Date getEstEnddate() {
        return estEnddate;
    }

    public void setEstEnddate(Date estEnddate) {
        this.estEnddate = estEnddate;
    }

    public Date getActStartdate() {
        return actStartdate;
    }

    public void setActStartdate(Date actStartdate) {
        this.actStartdate = actStartdate;
    }

    public Date getActEnddate() {
        return actEnddate;
    }

    public void setActEnddate(Date actEnddate) {
        this.actEnddate = actEnddate;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<Issued> getIssuedList() {
        return issuedList;
    }

    public void setIssuedList(List<Issued> issuedList) {
        this.issuedList = issuedList;
    }
}
