package com.askrindo.service;

import com.askrindo.entity.Project;

import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */
public interface ProjectService {
    public void saveProject(Project project);
    public List<Project> getAllProject();
    public Project getProjectById(String id);
    public void updateProject(Project project);
    public void updateStatusProjectById(String id);
    public List<Project> getProjectByStatus(String status);
    public List<Project> getProjectByStatusProject (String statusProject);
    public List<Project> getProjectByDirectorate (String directorate);
    public List<Project> getProjectByCategoryInitiative(String initiative);
}
