package com.askrindo.controller;

import com.askrindo.entity.Issued;
import com.askrindo.entity.Project;
import com.askrindo.service.IssuedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IssuedController {

    @Autowired
    IssuedService issuedService;

    @PostMapping("/issued")
    public void saveIssued(@RequestBody Issued issued){
        issuedService.saveIssued(issued);
    }

    @GetMapping("/issued/{id}")
    public Issued getIssuedById(@PathVariable String id){
        return issuedService.getIssuedById(id);
    }

    @GetMapping("/issueds")
    public List<Issued> getAllIssued(){
        return issuedService.getAllIssued();
    }

    @PutMapping("/changeStatusIssue/{id}")
    public void changeStatusIssueById(@PathVariable String id){
        issuedService.changeStatusIssue(id);
    }

    @PutMapping("/issued")
    public void updateIssued(@RequestBody Issued issued){
        issuedService.updateIssued(issued);
    }

    @GetMapping("/issuedByReleaseId/{id}")
    public List<Issued> getIssuedByReleaseId(@PathVariable String id){
        return issuedService.getIssuedByReleaseId(id);
    }
}

