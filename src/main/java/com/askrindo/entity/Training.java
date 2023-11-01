package com.askrindo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_training")
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Training extends Auditable<String> {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String competenceCode;

    private String competence;

    private String subCompetenceCode;

    private String subCompetence;

    @ManyToOne
    @JoinColumn(name = "idMasterTraining")
    private MasterTraining masterTraining;

    private String status;

    private String divisionCode;

    private String businessIssues;

    private String performanceIssues;

    private String competencyIssues;

    @ManyToOne
    @JoinColumn(name = "idUsers")
    private Users idUsers;

    private String trainingName;

    @JsonFormat(timezone = "Asia/Jakarta",pattern = "yyyy-MM-dd")
    private Date trainingDate;

    private Integer trainingParticipants;

    private String trainingType;

    private Double trainingCost;

    private Double consumptionCost;

    private Double accommodationCost;

    private String type;

    private String timeline;

    private String statusDesc;

    public Training() {
    }


}
