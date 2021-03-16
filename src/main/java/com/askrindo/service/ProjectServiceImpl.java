package com.askrindo.service;

import com.askrindo.dto.ProjectSearchDTO;
import com.askrindo.entity.Division;
import com.askrindo.entity.Project;
import com.askrindo.entity.User;
import com.askrindo.entity.Users;
import com.askrindo.entity.sequence.SequenceIdProject;
import com.askrindo.repository.ProjectRepository;
import com.askrindo.spesification.ProjectSpesification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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

    @Autowired
    DivisionService divisionService;

    @Autowired
    UserService userService;


    @Autowired
    SequenceIdProjectService sequenceIdProjectService;

    @Override
    public void saveProject(Project project) {
        List<Project> projectList = projectRepository.findProjectByStatusProject("aktif");
        Float totalScoreProject = Float.valueOf(0);
        Float totalScoreProject2 = Float.valueOf(0);
        for (Project project1 : projectList) {
            totalScoreProject = totalScoreProject + project1.getScore();
        }
        totalScoreProject2 = totalScoreProject + project.getScore();
        project.setWeight(project.getScore() / totalScoreProject2);
        for (Project project1 : projectList) {
            project1.setWeight(project1.getScore() / totalScoreProject2);
            projectRepository.save(project1);
        }

        if (project.getId() == null) {
            Division div = divisionService.getDivisionById(project.getDivisiUser().getId());
            SequenceIdProject sequenceIdProject = new SequenceIdProject();
            SequenceIdProject idProjectGen = sequenceIdProjectService.saveSequenceIdProject(sequenceIdProject);
            String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            String[] yearChar = year.split("");
            year = yearChar[2] + yearChar[3];
            String idProjectgenFormat = div.getDivisionCode() + "-" + year + "-" + idProjectGen.getIdGeneratorProject();
            project.setProjectCode(idProjectgenFormat);
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
        if (project.getStatusProject().equalsIgnoreCase("tidak aktif")) {
            project.setStatusProject("aktif");
            projectRepository.save(project);
        } else {
            project.setStatusProject("tidak aktif");
        }
        List<Project> projectList = projectRepository.findProjectByStatusProject("aktif");
        Float totalScoreProject = Float.valueOf(0);
        for (Project project1 : projectList) {
            totalScoreProject = totalScoreProject + project1.getScore();
        }
        for (Project project1 : projectList) {
            project1.setWeight(project1.getScore() / totalScoreProject);
            projectRepository.save(project1);
        }
    }

    @Override
    public List<Project> getProjectByStatusProject(String statusProject) {
        return projectRepository.findProjectByStatusProject(statusProject);
    }

    @Override
    public List<Project> getAllProjectforUser() {
        return null;
//        return projectRepository.getAllProjectforUser();
    }

    @Override
    public List<Project> getProjectByDivisiUserAndStatusProject(String divisiUser, String statusProject) {
        return projectRepository.findProjectByDivisiUserAndStatusProject(divisiUser, "aktif");
    }

    @Override
    public Page<Project> getProjects(Pageable pageable, ProjectSearchDTO projectSearchDTO) {
        Specification<Project> projectSpecification = ProjectSpesification.getSpesification(projectSearchDTO);
        return projectRepository.findAll(projectSpecification, pageable);
    }

    @Override
    public Project getProjectList(String divisiUser, String directorateUser, String pmId, String pmoId) {
        if(divisiUser.isEmpty() && !directorateUser.isEmpty() && !pmId.isEmpty() && !pmoId.isEmpty()){
            System.out.println("kosong");
            Users pm = userService.getUserById(pmId);
            Users pmo = userService.getUserById(pmoId);
            return projectRepository.findProjectAB(pm,pmo,directorateUser);
        }

        if(divisiUser.isEmpty() && directorateUser.isEmpty() && !pmId.isEmpty() && !pmoId.isEmpty()){
            Users pm = userService.getUserById(pmId);
            Users pmo = userService.getUserById(pmoId);
            return projectRepository.findProjectAC(pm,pmo);
        }

        if(divisiUser.isEmpty() && directorateUser.isEmpty() && pmId.isEmpty() && !pmoId.isEmpty()){
            Users pmo = userService.getUserById(pmoId);
            return projectRepository.findProjectAD(pmo);
        }

        if(divisiUser.isEmpty() && directorateUser.isEmpty() && !pmId.isEmpty() && pmoId.isEmpty()){
            Users pm = userService.getUserById(pmId);
            return projectRepository.findProjectAE(pm);
        }

        if(!divisiUser.isEmpty() && !directorateUser.isEmpty() && !pmId.isEmpty() && !pmoId.isEmpty()){
            System.out.println("masuk sini");
            Division division = divisionService.getDivisionByName(divisiUser);
            Users pm = userService.getUserById(pmId);
            Users pmo = userService.getUserById(pmoId);
            return projectRepository.findProjectAA(division,pm,pmo,directorateUser);
        }

        if(!divisiUser.isEmpty() && !directorateUser.isEmpty() && !pmId.isEmpty() && pmoId.isEmpty()){
            Division division = divisionService.getDivisionByName(divisiUser);
            Users pm = userService.getUserById(pmId);
            return projectRepository.findProjectAF(division,pm,directorateUser);
        }

        return null;
    }
}
