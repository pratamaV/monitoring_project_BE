package com.askrindo.service;

import com.askrindo.dto.SimpleEntity;
import com.askrindo.entity.Training;
import org.springframework.http.ResponseEntity;

public interface TrainingService {

    ResponseEntity<SimpleEntity.DefaultResponse> registerTraining(SimpleEntity.RegisterTraining request);

    ResponseEntity<SimpleEntity.DefaultResponse> findAllTrainingByIdUsers(String idUsers);

    ResponseEntity<SimpleEntity.DefaultResponse> findAllTrainingByDivisionCode(String divisionCode);

    ResponseEntity<SimpleEntity.DefaultResponse> findAllMasterTraining();

    ResponseEntity<SimpleEntity.DefaultResponse> findById(String id);

    ResponseEntity<SimpleEntity.DefaultResponse> findAllTrainingByType(String type);
}
