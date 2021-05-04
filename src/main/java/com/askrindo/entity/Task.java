package com.askrindo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "mst_task")
@EntityListeners(AuditingEntityListener.class)
public class Task extends Auditable<String> {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String taskName;
    private String taskCode;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private Users assignedTo;

    private Integer score;
    private Float weight;
    private String statusDone;
    private Float taskProsentase;

    @JsonFormat(timezone = "Asia/Jakarta",pattern = "yyyy-MM-dd")
    private Date estStartDate;

    @JsonFormat(timezone = "Asia/Jakarta",pattern = "yyyy-MM-dd")
    private Date actStartDate;

    @JsonFormat(timezone = "Asia/Jakarta",pattern = "yyyy-MM-dd")
    private Date estEndDate;

    @JsonFormat(timezone = "Asia/Jakarta",pattern = "yyyy-MM-dd")
    private Date actEndDate;
    private String taskDocument;

    @ManyToOne
    @JoinColumn(name = "release_id")
    private Release release;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("task")
//    @JsonIgnore
    private List<File> fileList = new ArrayList<>();

    public Task() {
    }


    public Task(String id, String taskName, String taskCode, Users assignedTo, Integer score, Float weight, String statusDone, Float taskProsentase, Date estEndDate, Release release) {
        this.id = id;
        this.taskName = taskName;
        this.taskCode = taskCode;
        this.assignedTo = assignedTo;
        this.score = score;
        this.weight = weight;
        this.statusDone = statusDone;
        this.taskProsentase = taskProsentase;
        this.estEndDate = estEndDate;
        this.release = release;
    }

    public Task(String taskName, Users assignedTo, Integer score, Float weight, String statusDone, Float taskProsentase, Date estEndDate, String taskDocument) {
        this.taskName = taskName;
        this.assignedTo = assignedTo;
        this.score = score;
        this.weight = weight;
        this.statusDone = statusDone;
        this.taskProsentase = taskProsentase;
        this.estEndDate = estEndDate;
        this.taskDocument = taskDocument;
    }


    public Task(String id, String taskName, Users assignedTo, Integer score, Float weight, String statusDone, Float taskProsentase, Date estEndDate, String taskDocument, Release release) {
        this.id = id;
        this.taskName = taskName;
        this.taskCode = taskCode;
        this.assignedTo = assignedTo;
        this.score = score;
        this.weight = weight;
        this.statusDone = statusDone;
        this.taskProsentase = taskProsentase;
        this.estEndDate = estEndDate;
        this.taskDocument = taskDocument;
        this.release = release;
    }

    public Task(String taskName, Users assignedTo, Integer score, Float weight, String statusDone, Float taskProsentase, Date estEndDate, String taskDocument, Release release) {
        this.taskName = taskName;
        this.assignedTo = assignedTo;
        this.score = score;
        this.weight = weight;
        this.statusDone = statusDone;
        this.taskProsentase = taskProsentase;
        this.estEndDate = estEndDate;
        this.taskDocument = taskDocument;
        this.release = release;
    }

    public Task(String taskName, Integer score, Float weight, String statusDone, Float taskProsentase, Date estEndDate, String taskDocument) {
        this.taskName = taskName;
        this.score = score;
        this.weight = weight;
        this.statusDone = statusDone;
        this.taskProsentase = taskProsentase;
        this.estEndDate = estEndDate;
        this.taskDocument = taskDocument;
    }

    public Task(String id, String taskName, String taskCode, Users assignedTo, Integer score, Float weight, String statusDone, Float taskProsentase, Date estEndDate, String taskDocument, Release release) {
        this.id = id;
        this.taskName = taskName;
        this.taskCode = taskCode;
        this.assignedTo = assignedTo;
        this.score = score;
        this.weight = weight;
        this.statusDone = statusDone;
        this.taskProsentase = taskProsentase;
        this.estEndDate = estEndDate;
        this.taskDocument = taskDocument;
        this.release = release;
    }

    public Task(String id, String taskName, String taskCode, Users assignedTo, Integer score, Float weight, String statusDone, Float taskProsentase, Date estStartDate, Date actStartDate, Date estEndDate, Date actEndDate, Release release) {
        this.id = id;
        this.taskName = taskName;
        this.taskCode = taskCode;
        this.assignedTo = assignedTo;
        this.score = score;
        this.weight = weight;
        this.statusDone = statusDone;
        this.taskProsentase = taskProsentase;
        this.estStartDate = estStartDate;
        this.actStartDate = actStartDate;
        this.estEndDate = estEndDate;
        this.actEndDate = actEndDate;
        this.release = release;
    }

    public Float getTaskProsentase() {
        return taskProsentase;
    }

    public void setTaskProsentase(Float taskProsentase) {
        this.taskProsentase = taskProsentase;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getStatusDone() {
        return statusDone;
    }

    public void setStatusDone(String statusDone) {
        this.statusDone = statusDone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public Date getEstEndDate() {
        return estEndDate;
    }

    public void setEstEndDate(Date finalTarget) {
        this.estEndDate = finalTarget;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getTaskDocument() {
        return taskDocument;
    }

    public void setTaskDocument(String taskDocument) {
        this.taskDocument = taskDocument;
    }

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public Users getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Users assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Date getEstStartDate() {
        return estStartDate;
    }

    public void setEstStartDate(Date estStartDate) {
        this.estStartDate = estStartDate;
    }

    public Date getActStartDate() {
        return actStartDate;
    }

    public void setActStartDate(Date actStartDate) {
        this.actStartDate = actStartDate;
    }

    public Date getActEndDate() {
        return actEndDate;
    }

    public void setActEndDate(Date actEndDate) {
        this.actEndDate = actEndDate;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }
}
