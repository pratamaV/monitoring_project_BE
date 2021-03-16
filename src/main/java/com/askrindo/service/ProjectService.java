package com.askrindo.service;

import com.askrindo.dto.ProjectSearchDTO;
import com.askrindo.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    public List<Project> getProjectByStatusProject(String statusProject);
    public List<Project> getAllProjectforUser();
    public List<Project> getProjectByDivisiUserAndStatusProject(String divisiUser, String statusProject);
    Page<Project> getProjects(Pageable pageable, ProjectSearchDTO projectSearchDTO);
    public Project getProjectList(String divisiUser, String directorateUser, String pm, String pmo);
}
