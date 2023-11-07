package com.askrindo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class SimpleEntity {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DefaultResponse {
        private String responseCode;
        private String responseStatus;
        private List<?> data;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterTraining {
        private String idTraining;
        private String competence;
        private String subCompetenceCode;
        private String idMasterTraining;
        private String idUsers;
        private String divisionCode;
        private String status;
        private String businessIssues;
        private String performanceIssues;
        private String competencyIssues;

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

        @Override
        public String toString() {
            return "RegisterTraining{" +
                    "idTraining='" + idTraining + '\'' +
                    ", competence='" + competence + '\'' +
                    ", subCompetenceCode='" + subCompetenceCode + '\'' +
                    ", idMasterTraining='" + idMasterTraining + '\'' +
                    ", idUsers='" + idUsers + '\'' +
                    ", divisionCode='" + divisionCode + '\'' +
                    ", status='" + status + '\'' +
                    ", businessIssues='" + businessIssues + '\'' +
                    ", performanceIssues='" + performanceIssues + '\'' +
                    ", competencyIssues='" + competencyIssues + '\'' +
                    ", trainingName='" + trainingName + '\'' +
                    ", trainingDate=" + trainingDate +
                    ", trainingParticipants=" + trainingParticipants +
                    ", trainingType='" + trainingType + '\'' +
                    ", trainingCost=" + trainingCost +
                    ", consumptionCost=" + consumptionCost +
                    ", accommodationCost=" + accommodationCost +
                    ", type='" + type + '\'' +
                    ", timeline='" + timeline + '\'' +
                    '}';
        }
    }
}
