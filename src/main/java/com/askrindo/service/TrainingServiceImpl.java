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
            Training training = new Training();
            training.setIdUsers(users);
            training.setStatus("0");
            training.setStatusDesc("Draft");
            training.setType(request.getType());
            training.setDivisionCode(request.getDivisionCode());
            if(request.getType().equalsIgnoreCase("T")) {
                MasterTraining masterTraining = masterTrainingRepository.findById(request.getIdMasterTraining()).get();

                training.setCompetence(request.getCompetence());
                training.setSubCompetenceCode(request.getSubCompetenceCode());
                training.setMasterTraining(masterTraining);
                training.setBusinessIssues(request.getBusinessIssues());
                training.setPerformanceIssues(request.getPerformanceIssues());
                training.setCompetencyIssues(request.getCompetencyIssues());
                training.setTrainingName(masterTraining.getTrainingName());
                if (request.getSubCompetenceCode().equals("1")) {
                    training.setSubCompetence("Risk Management");
                } else if (request.getSubCompetenceCode().equals("2")) {
                    training.setSubCompetence("Organization, Product & Insurance Knowledge");
                } else if (request.getSubCompetenceCode().equals("3")) {
                    training.setSubCompetence("Tech Savy");
                }
                trainingList.add(training);
                trainingRepository.save(training);
                return new ResponseEntity<>(new SimpleEntity.DefaultResponse("00", "Success", trainingList), HttpStatus.OK);
            } else {
                training.setTrainingName(request.getTrainingName());
                training.setTrainingDate(request.getTrainingDate());
                training.setTrainingParticipants(request.getTrainingParticipants());
                training.setTrainingType(request.getTrainingType());
                training.setTrainingCost(request.getTrainingCost());
                training.setConsumptionCost(request.getConsumptionCost());
                training.setAccommodationCost(request.getAccommodationCost());
                trainingList.add(training);
                trainingRepository.save(training);
                return new ResponseEntity<>(new SimpleEntity.DefaultResponse("00", "Success", trainingList), HttpStatus.OK);
            }
        } else {
            Optional<Training> training = trainingRepository.findById(request.getIdTraining());
            if(training.isPresent()){
                String statusDesc = "";
                if(request.getStatus().equals("0")){
                    statusDesc = "Draft";
                } else if (request.getStatus().equals("1")) {
                    statusDesc = "Submit";
                } else if (request.getStatus().equals("2")) {
                    statusDesc = "Approved";
                } else if (request.getStatus().equals("3")) {
                    statusDesc = "Reject";
                }
                if(request.getType().equalsIgnoreCase("T")) {
                    MasterTraining masterTraining = masterTrainingRepository.findById(request.getIdMasterTraining()).get();
                    training.get().setMasterTraining(masterTraining);
                    training.get().setTrainingName(masterTraining.getTrainingName());
                    training.get().setBusinessIssues(request.getBusinessIssues());
                    training.get().setPerformanceIssues(request.getPerformanceIssues());
                    training.get().setCompetencyIssues(request.getCompetencyIssues());
                    training.get().setStatus(request.getStatus());
                    training.get().setStatusDesc(statusDesc);
                    System.out.println(request.getTimeline());
                    if(request.getTimeline() != null){
                        training.get().setTimeline(request.getTimeline());
                    }
                    trainingList.add(training.get());
                    trainingRepository.save(training.get());
                    return new ResponseEntity<>(new SimpleEntity.DefaultResponse("00", "Success", trainingList), HttpStatus.OK);
                } else {
                    training.get().setTrainingName(request.getTrainingName());
                    training.get().setTrainingDate(request.getTrainingDate());
                    training.get().setTrainingParticipants(request.getTrainingParticipants());
                    training.get().setTrainingType(request.getTrainingType());
                    training.get().setTrainingCost(request.getTrainingCost());
                    training.get().setConsumptionCost(request.getConsumptionCost());
                    training.get().setAccommodationCost(request.getAccommodationCost());
                    training.get().setStatus(request.getStatus());
                    training.get().setStatusDesc(statusDesc);
                    trainingList.add(training.get());
                    trainingRepository.save(training.get());
                    return new ResponseEntity<>(new SimpleEntity.DefaultResponse("00", "Success", trainingList), HttpStatus.OK);
                }
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

    @Override
    public ResponseEntity<SimpleEntity.DefaultResponse> findAllTrainingByType(String type) {
        List<Training> trainingList = trainingRepository.findAllByTypeOrderByLastModifiedByDesc(type);
        return new ResponseEntity<>(new SimpleEntity.DefaultResponse("00", "Success", trainingList), HttpStatus.OK);
    }
}
