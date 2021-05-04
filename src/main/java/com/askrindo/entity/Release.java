package com.askrindo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "mst_release")
@EntityListeners(AuditingEntityListener.class)
public class Release extends Auditable<String> {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String releaseName;
    private String releaseCode;
    private Integer score;
    private Float weight;
    private String description;
    private String status;
    private String stage;
    private Float prosentaseRelease;
    private String statusRelease;
    private Float contractedValue;
    private String categoryActivity;

    @ManyToOne
    @JoinColumn(name = "pmo_id")
    private Users pmo;

    @ManyToOne
    @JoinColumn(name = "pm_id")
    private Users pm;

    @ManyToOne
    @JoinColumn(name = "coPM_id")
    private Users coPM;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division divisiUser;

    private String directorateUser;

    @ManyToOne
    @JoinColumn(name = "department_head")
    private Users departmentHead;

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

    @OneToMany(mappedBy = "release", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("release")
//    @JsonIgnore
    private List<Task> taskList = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    private List<Issued> issuedList = new ArrayList<>();

    private String developmentMode;

    public Release() {
    }

    public Release(String id, String releaseName, String releaseCode, Integer score, Float weight, String description, String status, String stage, Float prosentaseRelease, String statusRelease, Float contractedValue, String categoryActivity, Users pmo, Users pm, Users coPM, Division divisiUser, String directorateUser, Users departmentHead, Date estStartdate, Date estEnddate, Date actStartdate, Date actEnddate, Project project, List<Task> taskList, List<Issued> issuedList, String developmentMode) {
        this.id = id;
        this.releaseName = releaseName;
        this.releaseCode = releaseCode;
        this.score = score;
        this.weight = weight;
        this.description = description;
        this.status = status;
        this.stage = stage;
        this.prosentaseRelease = prosentaseRelease;
        this.statusRelease = statusRelease;
        this.contractedValue = contractedValue;
        this.categoryActivity = categoryActivity;
        this.pmo = pmo;
        this.pm = pm;
        this.coPM = coPM;
        this.divisiUser = divisiUser;
        this.directorateUser = directorateUser;
        this.departmentHead = departmentHead;
        this.estStartdate = estStartdate;
        this.estEnddate = estEnddate;
        this.actStartdate = actStartdate;
        this.actEnddate = actEnddate;
        this.project = project;
        this.taskList = taskList;
        this.issuedList = issuedList;
        this.developmentMode = developmentMode;
    }

    public Release(String releaseName, String releaseCode, Integer score, Float weight, String description, String status, String stage, Float prosentaseRelease, String statusRelease, Float contractedValue, String categoryActivity, Users pmo, Users pm, Users coPM, Division divisiUser, String directorateUser, Users departmentHead, Date estStartdate, Date estEnddate, Date actStartdate, Date actEnddate, Project project, List<Task> taskList, List<Issued> issuedList, String developmentMode) {
        this.releaseName = releaseName;
        this.releaseCode = releaseCode;
        this.score = score;
        this.weight = weight;
        this.description = description;
        this.status = status;
        this.stage = stage;
        this.prosentaseRelease = prosentaseRelease;
        this.statusRelease = statusRelease;
        this.contractedValue = contractedValue;
        this.categoryActivity = categoryActivity;
        this.pmo = pmo;
        this.pm = pm;
        this.coPM = coPM;
        this.divisiUser = divisiUser;
        this.directorateUser = directorateUser;
        this.departmentHead = departmentHead;
        this.estStartdate = estStartdate;
        this.estEnddate = estEnddate;
        this.actStartdate = actStartdate;
        this.actEnddate = actEnddate;
        this.project = project;
        this.taskList = taskList;
        this.issuedList = issuedList;
        this.developmentMode = developmentMode;
    }


    public String getDevelopmentMode() {
        return developmentMode;
    }

    public void setDevelopmentMode(String developmentMode) {
        this.developmentMode = developmentMode;
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

    public String getReleaseCode() {
        return releaseCode;
    }

    public void setReleaseCode(String releaseCode) {
        this.releaseCode = releaseCode;
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

    public String getStatusRelease() {
        return statusRelease;
    }

    public void setStatusRelease(String statusRelease) {
        this.statusRelease = statusRelease;
    }

    public Float getContractedValue() {
        return contractedValue;
    }

    public void setContractedValue(Float contractedValue) {
        this.contractedValue = contractedValue;
    }

    public String getCategoryActivity() {
        return categoryActivity;
    }

    public void setCategoryActivity(String categoryActivity) {
        this.categoryActivity = categoryActivity;
    }

    public Users getPmo() {
        return pmo;
    }

    public void setPmo(Users pmo) {
        this.pmo = pmo;
    }

    public Users getPm() {
        return pm;
    }

    public void setPm(Users pm) {
        this.pm = pm;
    }

    public Users getCoPM() {
        return coPM;
    }

    public void setCoPM(Users coPM) {
        this.coPM = coPM;
    }

    public Division getDivisiUser() {
        return divisiUser;
    }

    public void setDivisiUser(Division divisiUser) {
        this.divisiUser = divisiUser;
    }

    public String getDirectorateUser() {
        return directorateUser;
    }

    public void setDirectorateUser(String directorateUser) {
        this.directorateUser = directorateUser;
    }

    public Users getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(Users departmentHead) {
        this.departmentHead = departmentHead;
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
