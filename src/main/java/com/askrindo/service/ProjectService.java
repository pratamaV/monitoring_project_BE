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
//    public Page<Project> getAllProjectWithFilter(String divisionId,
//                                                 String pmId,
//                                                 String pmoId,
//                                                 String statusProject,
//                                                 String directoratUser, Pageable pageable);
    public Project getProjectById(String id);
    public void updateProject(Project project);
    public void updateStatusProjectById(String id, String projectStatus);
    public List<Project> getProjectByStatusProject(String statusProject);
    public List<Project> getAllProjectforUser();
//    public List<Project> getProjectByDivisiUserAndStatusProject(String divisiUser, String statusProject);
    Page<Project> getProjects(Pageable pageable, ProjectSearchDTO projectSearchDTO);
//    public List<Project> getProjectList(String divisiUser, String directorateUser, String pm, String pmo);
//    public List<Project> getProjectByPmID(String id);
//    public List<Project> getProjectByPmoID(String id);
//    public List<Project> getProjectByCoPmID(String id);
//    public List<Project> getProjectByKeyword(String keyword);
    public Page<Project> getAllProjectWithSort(String orderBy, String sort, Integer page, Integer sizePerpage);
    public List<Project> getProjectByLineItemBebanUsaha();
    public List<Project> getProjectByLineItemBelanjaModal();
    public List<Project> getAllProjectList();

    public Page<Project> getProjectByCoPMId(String id, String projectDependency, String projectName,Pageable pageable);

//    public Page<Project> getAllProjectPageFilter(String projectDependency, Pageable pageable);
    public Page<Project> getAllProjectPageFilter(String projectDependency, String projectName, Integer page, Integer sizePerpage, String orderBy , String sort);

    public List<Project> getProjectByDirectorateUser(String directorateUser);

}
