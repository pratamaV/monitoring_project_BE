package com.askrindo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;



@Entity
@Table(name = "mst_task")
public class Task {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String taskName;
    private String taskCode;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    private Integer score;
    private Float weight;
    private String statusDone;
    private Float taskProsentase;

    @JsonFormat(timezone = "Asia/Jakarta",pattern = "yyyy-MM-dd")
    private Date finalTarget;
    private String taskDocument;

    @ManyToOne
    @JoinColumn(name = "release_id")
    private Release release;

    public Task() {
    }

    public Task(String taskName, User assignedTo, Integer score, Float weight, String statusDone, Float taskProsentase, Date finalTarget, String taskDocument) {
        this.taskName = taskName;
        this.assignedTo = assignedTo;
        this.score = score;
        this.weight = weight;
        this.statusDone = statusDone;
        this.taskProsentase = taskProsentase;
        this.finalTarget = finalTarget;
        this.taskDocument = taskDocument;
    }

    public Task(String id, String taskName, User assignedTo, Integer score, Float weight, String statusDone, Float taskProsentase, Date finalTarget, String taskDocument, Release release) {
        this.id = id;
        this.taskName = taskName;
        this.assignedTo = assignedTo;
        this.score = score;
        this.weight = weight;
        this.statusDone = statusDone;
        this.taskProsentase = taskProsentase;
        this.finalTarget = finalTarget;
        this.taskDocument = taskDocument;
        this.release = release;
    }

    public Task(String taskName, User assignedTo, Integer score, Float weight, String statusDone, Float taskProsentase, Date finalTarget, String taskDocument, Release release) {
        this.taskName = taskName;
        this.assignedTo = assignedTo;
        this.score = score;
        this.weight = weight;
        this.statusDone = statusDone;
        this.taskProsentase = taskProsentase;
        this.finalTarget = finalTarget;
        this.taskDocument = taskDocument;
        this.release = release;
    }

    public Task(String taskName, Integer score, Float weight, String statusDone, Float taskProsentase, Date finalTarget, String taskDocument) {
        this.taskName = taskName;
        this.score = score;
        this.weight = weight;
        this.statusDone = statusDone;
        this.taskProsentase = taskProsentase;
        this.finalTarget = finalTarget;
        this.taskDocument = taskDocument;
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

    public Date getFinalTarget() {
        return finalTarget;
    }

    public void setFinalTarget(Date finalTarget) {
        this.finalTarget = finalTarget;
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

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }
}
