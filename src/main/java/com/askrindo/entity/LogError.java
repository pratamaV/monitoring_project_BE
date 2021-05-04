package com.askrindo.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mst_log_error")
@EntityListeners(AuditingEntityListener.class)
public class LogError extends Auditable<String>{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(length = 10000)
    private String errorMessage;

    private String function;
    private Date incidentDate;
    private Boolean isActive;

    public LogError(String id, String errorMessage, String function, Date incidentDate, Boolean isActive) {
        this.id = id;
        this.errorMessage = errorMessage;
        this.function = function;
        this.incidentDate = incidentDate;
        this.isActive = isActive;
    }

    public LogError(String errorMessage, String function, Date incidentDate, Boolean isActive) {
        this.errorMessage = errorMessage;
        this.function = function;
        this.incidentDate = incidentDate;
        this.isActive = isActive;
    }

    public LogError() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Date getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(Date incidentDate) {
        this.incidentDate = incidentDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "LogError{" +
                "id='" + id + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", function='" + function + '\'' +
                ", incidentDate=" + incidentDate +
                ", isActive=" + isActive +
                '}';
    }
}
