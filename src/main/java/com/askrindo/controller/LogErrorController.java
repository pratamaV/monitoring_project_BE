package com.askrindo.controller;

import com.askrindo.entity.LogError;
import com.askrindo.service.LogErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LogErrorController {

    @Autowired
    LogErrorService logErrorService;


    @PostMapping("/logerror")
    public void saveLogError(@RequestBody LogError logError){
        logErrorService.saveLogError(logError);
    }

    @GetMapping("/logerrors")
    public void getAllLogError(){
        logErrorService.getAllLogError();
    }

    @GetMapping("/logerror/{id}")
    public void getAllLogError(@PathVariable String id){
        logErrorService.getLogErrorById(id);
    }

    @PutMapping("/logerror")
    public void updateLogError(@RequestBody LogError logError){
        logErrorService.updateLogError(logError);
    }
}
