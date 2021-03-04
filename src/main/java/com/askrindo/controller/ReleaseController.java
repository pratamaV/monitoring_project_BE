package com.askrindo.controller;

import com.askrindo.entity.Project;
import com.askrindo.entity.Release;
import com.askrindo.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReleaseController {

    @Autowired
    ReleaseService releaseService;

    @PostMapping("/release")
    public void saveRelease(@RequestBody Release release){
        releaseService.saveRelease(release);
    }

    @GetMapping("/release/{id}")
    public Release getReleaseById(@PathVariable String id){
        return releaseService.getReleaseById(id);
    }

    @GetMapping("/releases")
    public List<Release> getAllRelease(){
        return releaseService.getAllRelease();
    }

    @PutMapping("/release")
    public void updateRelease(@RequestBody Release release){
        releaseService.updateRelease(release);
    }

    @GetMapping("/releasesByStage/{stage}")
    public List<Release> getReleaseByStage(@PathVariable String stage){
        return releaseService.getReleaseByStage(stage);
    }

    @GetMapping("/releasesByStatus/{status}")
    public List<Release> getReleaseByStatus(@PathVariable String status){
        return releaseService.getReleaseByStatus(status);
    }

    @PutMapping("/releaseUpdate/{id}")
    public void updateStatusReleaseById(@PathVariable String id){
        releaseService.updateStatusReleaseById(id);
    }

    @GetMapping("/releaseByProjectId/{id}")
    public List<Release> getReleaseByProjectId(@PathVariable String id){
        return releaseService.getReleaseByProjectId(id);
    }
}
