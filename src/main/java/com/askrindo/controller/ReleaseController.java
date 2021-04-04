package com.askrindo.controller;

import com.askrindo.entity.Release;
import com.askrindo.service.ProjectService;
import com.askrindo.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReleaseController {

    @Autowired
    ReleaseService releaseService;

    @Autowired
    ProjectService projectService;

    @PostMapping("/release")
    public void saveRelease(@RequestBody Release release) {
        releaseService.saveRelease(release);
    }
//
//    @GetMapping("/releases-seacrh")
//    public List<Release> searchProduct(@RequestParam(name = "status", required = false) String status,
//                                       @RequestParam(name = "stage", required = false) String stage
//    ) {
//        Project project = new Project();
//        ReleaseSearchDTO releaseSearchDTO = new ReleaseSearchDTO(project, status, stage);
//        return releaseService.getReleaseList(releaseSearchDTO);
//    }

    @GetMapping("/release/{id}")
    public Release getReleaseById(@PathVariable String id) {
        return releaseService.getReleaseById(id);
    }

    @GetMapping("/releases")
    public List<Release> getAllRelease() {
        return releaseService.getAllRelease();
    }

    @PutMapping("/release")
    public void updateRelease(@RequestBody Release release) {
        releaseService.updateRelease(release);
    }

    @GetMapping("/releasesByStage/{stage}")
    public List<Release> getReleaseByStage(@PathVariable String stage) {
        return releaseService.getReleaseByStage(stage);
    }

    @GetMapping("/releasesByStatus/{status}")
    public List<Release> getReleaseByStatus(@PathVariable String status) {
        return releaseService.getReleaseByStatus(status);
    }

    @PutMapping("/releaseUpdate/{id}")
    public void updateStatusReleaseById(@PathVariable String id,
                                        @RequestParam String releaseStatus) {
        releaseService.updateStatusReleaseById(id, releaseStatus);
    }

    @GetMapping("/releaseByProjectId/{id}")
    public List<Release> searchReleaseByProjectId(@PathVariable String id,
                                                  @RequestParam(name = "status", required = false) String status,
                                                  @RequestParam(name = "stage", required = false) String stage
    ) {
        if (status == null && stage == null) {
            status = "";
            stage = "";
        } else if (status == null) {
            status = "";
        } else if (stage == null) {
            stage = "";
        }
        return releaseService.getReleaseByProjectId(id, status, stage);
    }

//    @GetMapping("/releaseCode")
//    public String TestGenerateReleaseCode() {
//
//        return releaseService.generateReleaseCode("2c951081786843430178685388f40000");
//    }

//    @GetMapping("/releaseByProjectId/{id}")
//    public List<Release> getReleaseByProjectId(@PathVariable String id){
//        return releaseService.getReleaseByProjectId(id);
//    }
}
