package com.askrindo.controller;

import com.askrindo.dto.ProjectSearchDTO;
import com.askrindo.entity.Division;
import com.askrindo.entity.Project;
import com.askrindo.entity.sequence.SequenceIdProject;
import com.askrindo.service.DivisionService;
import com.askrindo.service.ProjectService;
import com.askrindo.service.SequenceIdProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.Date;
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

    @GetMapping("/project/{id}")
    public Project getProjectById(@PathVariable String id){
        return projectService.getProjectById(id);
    }

    @GetMapping("/projects")
    public List<Project> getAllProject(){
        return projectService.getAllProject();
    }

    @GetMapping("/projectbyuser")
    public List<Project> getAllProjectforUser(){
        return projectService.getAllProjectforUser();
    }

    @PutMapping("/project")
    public void updateProject(@RequestBody Project project){
        projectService.updateProject(project);
    }

    @PutMapping("/project/{id}")
    public void updateStatusProjectById(@PathVariable String id){
        projectService.updateStatusProjectById(id);
    }

    @GetMapping("/test")
    public void test(){
        SequenceIdProject sequenceIdProject = new SequenceIdProject();
        SequenceIdProject idProjectGen = sequenceIdProjectService.saveSequenceIdProject(sequenceIdProject);
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String [] yearChar = year.split("");
        year = yearChar[2]+yearChar[3];
        String idProjectgenFormat = year +"-" + idProjectGen.getIdGeneratorProject();
        System.out.println(idProjectgenFormat);

    }

    @GetMapping("/projectByDivision")
    public List<Project> getProjectByDivisiUserAndStatusProject(@RequestParam String divisiUser,
                                                                @RequestParam String statusProject){
        return projectService.getProjectByDivisiUserAndStatusProject(divisiUser, statusProject);
    }

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

    @GetMapping("/project")
    public List<Project> searchProject(@RequestParam(name = "divisiUser", required = false) String divisiUser,
                                       @RequestParam(name = "directorateUser", required = false) String directorateUser,
                                       @RequestParam(name = "pm", required = false) String pm,
                                       @RequestParam(name = "pmo", required = false) String pmo){
        System.out.println("divisi usernya adlaha: " + divisiUser);
        return  projectService.getProjectList(divisiUser, directorateUser, pm, pmo);
    }

}
