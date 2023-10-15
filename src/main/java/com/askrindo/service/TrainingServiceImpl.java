package com.askrindo.service;

import com.askrindo.dto.SimpleEntity;
import com.askrindo.entity.MasterTraining;
import com.askrindo.entity.Training;
import com.askrindo.entity.Users;
import com.askrindo.repository.MasterTrainingRepository;
import com.askrindo.repository.TrainingRepository;
import com.askrindo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService{

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    MasterTrainingRepository masterTrainingRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<SimpleEntity.DefaultResponse> registerTraining(SimpleEntity.RegisterTraining request) {

        System.out.println(request.toString());

        final List<Training> trainingList = new ArrayList<>();
        if(request.getIdTraining() == null){
            Users users = userRepository.findById(request.getIdUsers()).get();
            MasterTraining masterTraining = masterTrainingRepository.findById(request.getIdMasterTraining()).get();

            Training training = new Training();
            training.setCompetence(request.getCompetence());
            training.setSubCompetenceCode(request.getSubCompetenceCode());
            training.setIdUsers(users);
            training.setMasterTraining(masterTraining);
            training.setDivisionCode(request.getDivisionCode());
            training.setBusinessIssues(request.getBusinessIssues());
            training.setPerformanceIssues(request.getPerformanceIssues());
            training.setCompetencyIssues(request.getCompetencyIssues());
            if(request.getSubCompetenceCode().equals("1")){
                training.setSubCompetence("Risk Management");
            } else if(request.getSubCompetenceCode().equals("2")){
                training.setSubCompetence("Organization, Product & Insurance Knowledge");
            } else if(request.getSubCompetenceCode().equals("3")){
                training.setSubCompetence("Tech Savy");
            }
            training.setStatus("0");
            trainingList.add(training);
            trainingRepository.save(training);
            return new ResponseEntity<>(new SimpleEntity.DefaultResponse("00", "Success", trainingList), HttpStatus.OK);
        } else {
            Optional<Training> training = trainingRepository.findById(request.getIdTraining());
            if(training.isPresent()){
                MasterTraining masterTraining = masterTrainingRepository.findById(request.getIdMasterTraining()).get();
                training.get().setMasterTraining(masterTraining);
                training.get().setStatus(request.getStatus());
                training.get().setBusinessIssues(request.getBusinessIssues());
                training.get().setPerformanceIssues(request.getPerformanceIssues());
                training.get().setCompetencyIssues(request.getCompetencyIssues());
                trainingList.add(training.get());
                trainingRepository.save(training.get());
                return new ResponseEntity<>(new SimpleEntity.DefaultResponse("00", "Success", trainingList), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new SimpleEntity.DefaultResponse("01", "Id training not found", null), HttpStatus.NOT_FOUND);
            }
        }
    }

    @Override
    public ResponseEntity<SimpleEntity.DefaultResponse> findAllTrainingByIdUsers(String idUsers) {
        Optional<Users> users = userRepository.findById(idUsers);
        if(users.isPresent()){
            List<Training> trainingList = trainingRepository.findAllByIdUsersOrderByLastModifiedByDesc(users.get());
            return new ResponseEntity<>(new SimpleEntity.DefaultResponse("00", "Success", trainingList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new SimpleEntity.DefaultResponse("01", "Id users not found", null), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<SimpleEntity.DefaultResponse> findAllTrainingByDivisionCode(String divisionCode) {
        List<Training> trainingList = trainingRepository.findAllByDivisionCodeOrderByLastModifiedByDesc(divisionCode);
        return new ResponseEntity<>(new SimpleEntity.DefaultResponse("00", "Success", trainingList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SimpleEntity.DefaultResponse> findAllMasterTraining() {
        List<MasterTraining> masterTrainings = masterTrainingRepository.findAll();
        return new ResponseEntity<>(new SimpleEntity.DefaultResponse("00", "Success", masterTrainings), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SimpleEntity.DefaultResponse> findById(String id) {
        Optional<Training> training = trainingRepository.findById(id);
        List<Training> trainingList = new ArrayList<>();
        trainingList.add(training.get());
        return new ResponseEntity<>(new SimpleEntity.DefaultResponse("00", "Success", trainingList), HttpStatus.OK);
    }
}
