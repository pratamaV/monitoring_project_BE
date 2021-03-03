package com.askrindo.service;

import com.askrindo.entity.Project;
import com.askrindo.entity.Task;
import com.askrindo.entity.User;
import com.askrindo.exception.DataNotFoundException;
import com.askrindo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ReleaseService releaseService;

    @Override
    public void saveProject(Project project) {
        List<Project> projectList = projectRepository.findAll();
        Float totalScoreProject = Float.valueOf(0);
        Float totalScoreProject2 = Float.valueOf(0);
        for (Project project1: projectList) {
            totalScoreProject = totalScoreProject + project1.getScore();
        }
        totalScoreProject2 = totalScoreProject + project.getScore();
        project.setWeight(project.getScore()/totalScoreProject2);
        for (Project project1: projectList) {
            project1.setWeight(project1.getScore()/totalScoreProject2);
            projectRepository.save(project1);
        }
        projectRepository.save(project);
    }

    @Override
    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(String id) {
        return projectRepository.findById(id).get();
    }

    @Override
    public void updateProject(Project project) {
        projectRepository.save(project);
    }

    @Override
    public void updateStatusProjectById(String id) {
        Project project = projectRepository.findById(id).get();
        if (project.getStatusProject().equalsIgnoreCase("tidak aktif")){
            project.setStatusProject("aktif");
            projectRepository.save(project);
        } else {
            project.setStatusProject("tidak aktif");
        }
    }

    @Override
    public List<Project> getProjectByStatus(String status) {
        return projectRepository.findProjectByStatus(status);
    }

    @Override
    public List<Project> getProjectByStatusProject(String statusProject) {
        return projectRepository.findProjectByStatusProject(statusProject);
    }

    @Override
    public List<Project> getProjectByDirectorate(String directorate) {
        return projectRepository.findProjectByDirectorateUser(directorate);
    }

    @Override
    public List<Project> getProjectByCategoryInitiative(String initiative) {
        return projectRepository.findProjectByCategoryInitiative(initiative);
    }
}
