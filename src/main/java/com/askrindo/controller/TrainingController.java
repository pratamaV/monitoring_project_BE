package com.askrindo.controller;

import com.askrindo.dto.SimpleEntity;
import com.askrindo.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TrainingController {

    @Autowired
    TrainingService trainingService;

    @PostMapping("/training/register")
    public ResponseEntity<SimpleEntity.DefaultResponse> registerTraining(@RequestBody SimpleEntity.RegisterTraining request){
        return trainingService.registerTraining(request);
    }

    @GetMapping("/training/users/{idUsers}")
    public ResponseEntity<SimpleEntity.DefaultResponse> findAllTrainingByIdUsers(@PathVariable String idUsers){
        return trainingService.findAllTrainingByIdUsers(idUsers);
    }

    @GetMapping("/training/division/{divisionCode}")
    public ResponseEntity<SimpleEntity.DefaultResponse> findAllByDivisionCode(@PathVariable String divisionCode){
        return trainingService.findAllTrainingByDivisionCode(divisionCode);
    }

    @GetMapping("/training/master")
    public ResponseEntity<SimpleEntity.DefaultResponse> findAllTrainingMaster(){
        return trainingService.findAllMasterTraining();
    }

    @GetMapping("/training/{id}")
    public ResponseEntity<SimpleEntity.DefaultResponse> getTrainingById(@PathVariable String id){
        return trainingService.findById(id);
    }

    @GetMapping("/training/type/{type}")
    public ResponseEntity<SimpleEntity.DefaultResponse> findAllByTrainingType(@PathVariable String type){
        System.out.println("ini type : " + type);
        return trainingService.findAllTrainingByType(type);
    }
}
