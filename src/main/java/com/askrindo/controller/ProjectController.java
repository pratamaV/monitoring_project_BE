package com.askrindo.controller;

import com.askrindo.entity.Project;
import com.askrindo.entity.sequence.SequenceIdProject;
import com.askrindo.service.ProjectService;
import com.askrindo.service.SequenceIdProjectService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
