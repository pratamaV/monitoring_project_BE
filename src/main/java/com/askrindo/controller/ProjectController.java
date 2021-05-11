package com.askrindo.controller;

import com.askrindo.entity.Project;
import com.askrindo.entity.sequence.SequenceIdProject;
import com.askrindo.service.DivisionService;
import com.askrindo.service.ProjectService;
import com.askrindo.service.SequenceIdProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    DivisionService divisionService;


    @Autowired
    SequenceIdProjectService sequenceIdProjectService;

    @PostMapping("/project")
    public void saveProject(@RequestBody Project project){
        projectService.saveProject(project);
    }

    @PostMapping("/projects")
    public void saveProjectArray(@RequestBody List<Project> projectList){
        for (Project project: projectList) {
            projectService.saveProject(project);
        }
    }

    @GetMapping("/project/{id}")
    public Project getProjectById(@PathVariable String id){
        return projectService.getProjectById(id);
    }

    @GetMapping("/projects-page")
    public Page<Project> getAllProjectPage(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer sizePerPage,
            @RequestParam(name = "projectDependency", required = false) String projectDependency,
            @RequestParam(name = "projectName", required = false) String projectName,
            @RequestParam(name = "orderBy", required = false) String orderBy,
            @RequestParam(name = "sort", required = false) String sort){
//        Pageable pageable = PageRequest.of(page, sizePerPage);
//        String projectDependency, Integer page, Integer sizePerpage, String orderBy , String sort
        return projectService.getAllProjectPageFilter(projectDependency, projectName, page, sizePerPage, orderBy, sort);
    }

    @GetMapping("/my-project/{id}")
    public Page<Project> getMyProject(@PathVariable String id,
                                      @RequestParam(name = "page", defaultValue = "0") Integer page,
                                      @RequestParam(name = "size", defaultValue = "10") Integer sizePerPage,
                                      @RequestParam(name = "projectDependency", required = false) String projectDependency,
                                      @RequestParam(name = "projectName", required = false) String projectName){

        Pageable pageable = PageRequest.of(page, sizePerPage);
//        return projectService.getProjectByCoPMId(id, pageable);
        return projectService.getProjectByCoPMId(id, projectDependency, projectName, pageable);
    }

//    @GetMapping("/projects")
//    public Page<Project> getAllProject(
//            @RequestParam(name = "page", defaultValue = "0") Integer page,
//            @RequestParam(name = "size", defaultValue = "10") Integer sizePerPage,
//            @RequestParam(name = "divisionId", required = false) String divisionId,
//            @RequestParam(name = "pmId", required = false) String pmId,
//            @RequestParam(name = "pmoId", required = false) String pmoId,
//            @RequestParam(name = "statusProject", required = false) String statusProject,
//            @RequestParam(name = "directoratUser", required = false) String directoratUser) {
//
//        if (divisionId == null ) {
//            divisionId = "";
//        }
//        if (pmId == null) {
//            pmId = "";
//        }
//        if (pmoId == null) {
//            pmoId = "";
//        }
//        if (statusProject == null) {
//            statusProject = "";
//        }
//        if (directoratUser == null) {
//            directoratUser = "";
//        }
//        Pageable pageable = PageRequest.of(page, sizePerPage);
//        return projectService.getAllProjectWithFilter(divisionId, pmId, pmoId, statusProject, directoratUser, pageable);
//    }

    @GetMapping("/projects-list")
    public List<Project> getAllProject(){
            return projectService.getAllProjectList();
    }

    @GetMapping("/projects-sort")
    public Page<Project> getAllProjectWithSort(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer sizePerPage,
            @RequestParam(name = "orderBy", required = false) String orderBy,
            @RequestParam(name = "sort", required = false) String sort) {
            return projectService.getAllProjectWithSort(orderBy, sort, page, sizePerPage);
    }


    @GetMapping("/projectbyuser")
    public List<Project> getAllProjectforUser(){
        return projectService.getAllProjectforUser();
    }

    @GetMapping("/projectByDirectorateUser")
    public List<Project> getProjectByDirectorateUser(@RequestParam String directorateUser){
        return projectService.getProjectByDirectorateUser(directorateUser);
    }

    @PutMapping("/project")
    public void updateProject(@RequestBody Project project){
        projectService.updateProject(project);
    }

    @PutMapping("/project/{id}")
    public void updateStatusProjectById(@PathVariable String id,
                                        @RequestParam String projectStatus){
        projectService.updateStatusProjectById(id, projectStatus);
    }


//    @GetMapping("/testSortProject")
//    public Page<Project> test(@RequestParam(name = "page", defaultValue = "0") Integer page,
//                              @RequestParam(name = "size", defaultValue = "10") Integer sizePerPage){
////        SequenceIdProject sequenceIdProject = new SequenceIdProject();
////        SequenceIdProject idProjectGen = sequenceIdProjectService.saveSequenceIdProject(sequenceIdProject);
////        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
////        String [] yearChar = year.split("");
////        year = yearChar[2]+yearChar[3];
////        String idProjectgenFormat = year +"-" + idProjectGen.getIdGeneratorProject();
////        System.out.println(idProjectgenFormat);
////        Pageable pageable = PageRequest.of(page, sizePerPage);
////        return projectService.getAllProjectWithSort("projectName", "ASC", pageable);
//        return projectService.getAllProjectWithSort("projectName", "ASC", page, sizePerPage);
//    }

//    @GetMapping("/projectByDivision")
//    public List<Project> getProjectByDivisiUserAndStatusProject(@RequestParam String divisiUser,
//                                                                @RequestParam String statusProject){
//        return projectService.getProjectByDivisiUserAndStatusProject(divisiUser, statusProject);
//    }

//    @GetMapping("/project")
//    public Page<Project> searchProject(@RequestParam(name = "page" , defaultValue = "0") Integer page,
//                                       @RequestParam(name = "size" , defaultValue = "10") Integer sizePerPage,
//                                       @RequestParam(name = "divisionName", required = false) String divisionName,
//                                       @RequestParam(name = "catAct", required = false) String catAct,
//                                       @RequestParam(name = "pm", required = false) String pm,
//                                       @RequestParam(name = "pmo", required = false) String pmo,
//                                       @RequestParam(name = "copm", required = false) String copm,
//                                       @RequestParam(name = "status", required = false) String status){
////        Pageable pageable = PageRequest.of(page, sizePerPage);
////        Division division = divisionService.getDivisionByName(divisionName);
////        System.out.println("getDiv: " + division.getDivisionName());
////        ProjectSearchDTO projectSearchDTO = new ProjectSearchDTO(division, catAct, pm, pmo, copm, status);
////        return  projectService.getProjects(pageable, projectSearchDTO);
//    }

//    @GetMapping("/project")
//    public List<Project> searchProject(@RequestParam(name = "divisiUser", required = false) String divisiUser,
//                                       @RequestParam(name = "directorateUser", required = false) String directorateUser,
//                                       @RequestParam(name = "pm", required = false) String pm,
//                                       @RequestParam(name = "pmo", required = false) String pmo){
//        System.out.println("divisi usernya adlaha: " + divisiUser);
//        return  projectService.getProjectList(divisiUser, directorateUser, pm, pmo);
//    }

//    @GetMapping("/projectByPM/{id}")
//    public List<Project> getProjectByPMID(@PathVariable String id){
//       return projectService.getProjectByPmID(id);
//    }
//
//    @GetMapping("/projectByPMO/{id}")
//    public List<Project> getProjectByPMOID(@PathVariable String id){
//        return projectService.getProjectByPmoID(id);
//    }
//
//    @GetMapping("/projectBycoPM/{id}")
//    public List<Project> getProjectByCoPMID(@PathVariable String id){
//        return projectService.getProjectByCoPmID(id);
//    }
//
//    @GetMapping("/projectByKeyword/{keyword}")
//    public List<Project> getProjectByKeyword(@PathVariable String keyword){
//        return projectService.getProjectByKeyword(keyword);
//    }

    @GetMapping("/projectByBebanUsaha")
    public List<Project> getProjectByLineItemBebanUsaha(){
        return projectService.getProjectByLineItemBebanUsaha();
    }

    @GetMapping("/projectByBelanjaModal")
    public List<Project> getProjectByLineItemBelanjaModal(){
        return projectService.getProjectByLineItemBelanjaModal();
    }

//    @GetMapping("/my-project/{id}")
//    public Page<Project> getMyProject(@PathVariable String id,
//                                      @RequestParam(name = "page", defaultValue = "0") Integer page,
//                                        @RequestParam(name = "size", defaultValue = "10") Integer sizePerPage){
//
//        Pageable pageable = PageRequest.of(page, sizePerPage);
//        return projectService.getProjectByCoPMId(id, pageable);
//    }



}
