package com.askrindo.controller;

import com.askrindo.entity.Project;
import com.askrindo.entity.Release;
import com.askrindo.service.ProjectService;
import com.askrindo.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @PostMapping("/releases")
    public void saveReleaseArray(@RequestBody List<Release> releaseList) {
        for (Release release: releaseList) {
            releaseService.saveRelease(release);
        }
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

    @GetMapping("/releasesPage")
    public Page<Release> getAllReleasePage(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                           @RequestParam(name = "size", defaultValue = "10") Integer sizePerPage,
                                           @RequestParam(name = "projectName", required = false) String projectName,
                                           @RequestParam(name = "pmId", required = false) String pmId,
                                           @RequestParam(name = "pmoId", required = false) String pmoId,
                                           @RequestParam(name = "copmId", required = false) String copmId,
                                           @RequestParam(name = "status", required = false) String status,
                                           @RequestParam(name = "stage", required = false) String stage,
                                           @RequestParam(name = "divisionId", required = false) String divisionId,
                                           @RequestParam(name = "directoratUser", required = false) String directoratUser,
                                           @RequestParam(name = "developmentMode", required = false) String developmentMode) {
        Pageable pageable = PageRequest.of(page, sizePerPage);
        if (projectName == null) {
            projectName = "";
        }
        if (pmId == null) {
            pmId = "";
        }
        if (pmoId == null) {
            pmoId = "";
        }
        if (copmId == null) {
            copmId = "";
        }
        if (status == null) {
            status = "";
        }
        if (stage == null) {
            stage = "";
        }
        if (divisionId == null) {
            divisionId = "";
        }
        if (directoratUser == null) {
            directoratUser = "";
        }
        if (developmentMode == null) {
            developmentMode = "";
        }
        return releaseService.getAllReleasePage(projectName, pmId, pmoId, copmId, status, stage, divisionId, directoratUser, developmentMode, pageable);
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
    public Page<Release> searchReleaseByProjectId(@PathVariable String id,
                                                  @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(name = "size", defaultValue = "10") Integer sizePerPage,
                                                  @RequestParam(name = "pmId", required = false) String pmId,
                                                  @RequestParam(name = "pmoId", required = false) String pmoId,
                                                  @RequestParam(name = "copmId", required = false) String copmId,
                                                  @RequestParam(name = "status", required = false) String status,
                                                  @RequestParam(name = "stage", required = false) String stage,
                                                  @RequestParam(name = "divisionId", required = false) String divisionId,
                                                  @RequestParam(name = "directoratUser", required = false) String directoratUser,
                                                  @RequestParam(name = "projectCode", required = false) String projectCode,
                                                  @RequestParam(name = "projectName", required = false) String projectName,
                                                  @RequestParam(name = "developmentMode", required = false) String developmentMode) {


        if (pmId == null) {
            pmId = "";
        }
        if (pmoId == null) {
            pmoId = "";
        }
        if (copmId == null) {
            copmId = "";
        }
        if (status == null) {
            status = "";
        }
        if (stage == null) {
            stage = "";
        }
        if (divisionId == null) {
            divisionId = "";
        }
        if (directoratUser == null) {
            directoratUser = "";
        }
        if (projectCode == null) {
            projectCode = "";
        }
        if (projectName == null) {
            projectName = "";
        }
        if (developmentMode == null) {
            developmentMode = "";
        }
        Pageable pageable = PageRequest.of(page, sizePerPage);
        return releaseService.getReleaseByProjectId(id, pmId, pmoId, copmId, status, stage, divisionId, directoratUser, projectCode, projectName, developmentMode, pageable);
    }

    @GetMapping("/releaseByProjectId-sort/{id}")
    public List<Release> getReleaseByProjectIdWithSort(@PathVariable String id,
                                                       @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(name = "size", defaultValue = "10") Integer sizePerPage,
                                                       @RequestParam(name = "orderBy", required = false) String orderBy,
                                                       @RequestParam(name = "sort", required = false) String sort
    ) {
        return releaseService.getReleaseByProjectIdWithSort(id, orderBy, sort, page, sizePerPage);
    }


    @GetMapping("/release-sort")
    public Page<Release> getReleaseWithSort(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                            @RequestParam(name = "size", defaultValue = "10") Integer sizePerPage,
                                            @RequestParam(name = "orderBy", required = false) String orderBy,
                                            @RequestParam(name = "sort", required = false) String sort
    ) {
        return releaseService.getReleaseWithSort(orderBy, sort, page, sizePerPage);
    }

    @GetMapping("/releases-search")
    public Page<Release> getAllReleaseSearch(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                           @RequestParam(name = "size", defaultValue = "10") Integer sizePerPage,
                                           @RequestParam(name = "projectName", required = false) String projectName,
                                             @RequestParam(name = "projectDependency", required = false) String projectDependency) {
        Pageable pageable = PageRequest.of(page, sizePerPage);
        return releaseService.getAllReleaseSearch(projectDependency, projectName, pageable);
    }

//    @GetMapping("/testSortRelease")
//    public List<Release> TestSortRelease() {
//
//        return releaseService.getReleaseByProjectIdWithSort("2c9ba081789cff4d01789d35d3500007", "release_name", "ASC");
//    }

//    @GetMapping("/releaseByProjectId/{id}")
//    public List<Release> getReleaseByProjectId(@PathVariable String id){
//        return releaseService.getReleaseByProjectId(id);
//    }


}
