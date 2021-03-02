package com.askrindo.controller;

import com.askrindo.entity.Project;
import com.askrindo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    ProjectService projectService;

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
}
