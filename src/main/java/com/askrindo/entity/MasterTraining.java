package com.askrindo.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "m_training")
public class MasterTraining {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String trainingName;

    private String subCompetenceCode;

    public MasterTraining() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public String getSubCompetenceCode() {
        return subCompetenceCode;
    }

    public void setSubCompetenceCode(String subCompetenceCode) {
        this.subCompetenceCode = subCompetenceCode;
    }
}
