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

        List<Project> projectList = projectRepository.findProjectByStatusProject("Active");
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
    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> getAllProjectWithFilter(String divisionId,
                                                 String pmId,
                                                 String pmoId,
                                                 String statusProject,
                                                 String directoratUser) {
        return projectRepository.getAllProject(divisionId, pmId, pmoId, statusProject, directoratUser);
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
    public void updateStatusProjectById(String id, String projectStatus) {
        Project project = projectRepository.findById(id).get();
        project.setStatusProject(projectStatus);
        if(projectStatus.equalsIgnoreCase("Not Active")){
            project.setWeight(0.0f);
        }
        projectRepository.save(project);
        List<Project> projectList = projectRepository.findProjectByStatusProject("Active");
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
        return projectRepository.findProjectByDivisiUserAndStatusProject(divisiUser, "Active");
    }

    @Override
    public Page<Project> getProjects(Pageable pageable, ProjectSearchDTO projectSearchDTO) {
        Specification<Project> projectSpecification = ProjectSpesification.getSpesification(projectSearchDTO);
        return projectRepository.findAll(projectSpecification, pageable);
    }

    @Override
    public List<Project> getProjectList(String divisiUser, String directorateUser, String pmId, String pmoId) {
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

        if(!divisiUser.isEmpty() && !directorateUser.isEmpty() && pmId.isEmpty() && !pmoId.isEmpty()){
            System.out.println("masuk sini");
            Division division = divisionService.getDivisionByName(divisiUser);
            Users pmo = userService.getUserById(pmoId);
            return projectRepository.findProjectAI(division,pmo,directorateUser);
        }

        if(!divisiUser.isEmpty() && !directorateUser.isEmpty() && !pmId.isEmpty() && pmoId.isEmpty()){
            System.out.println("masuk sini dia yo");
            Division division = divisionService.getDivisionByName(divisiUser);
            Users pm = userService.getUserById(pmId);
            System.out.println("pm nya adalah: " + pm);
            System.out.println("direktorateuser: " + directorateUser);
            System.out.println("divisionnyaUser: " + division);
            return projectRepository.findProjectAF(division,pm,directorateUser);
        }

        if(!divisiUser.isEmpty() && directorateUser.isEmpty() && pmId.isEmpty() && pmoId.isEmpty()){
            System.out.println("divisi doang disini");
            Division division = divisionService.getDivisionByName(divisiUser);
            return projectRepository.findProjectAG(division);
        }

        if(!divisiUser.isEmpty() && !directorateUser.isEmpty() && pmId.isEmpty() && pmoId.isEmpty()){
            Division division = divisionService.getDivisionByName(divisiUser);
            return projectRepository.findProjectAH(division,directorateUser);
        }

        if(!divisiUser.isEmpty() && directorateUser.isEmpty() && !pmId.isEmpty() && pmoId.isEmpty()){
            Division division = divisionService.getDivisionByName(divisiUser);
            Users pm = userService.getUserById(pmId);
            return projectRepository.findProjectAJ(division,pm);
        }

        if(divisiUser.isEmpty() && !directorateUser.isEmpty() && pmId.isEmpty() && !pmoId.isEmpty()){
            Division division = divisionService.getDivisionByName(divisiUser);
            Users pmo = userService.getUserById(pmoId);
            return projectRepository.findProjectAK(pmo,directorateUser);
        }

        if(divisiUser.isEmpty() && !directorateUser.isEmpty() && !pmId.isEmpty() && pmoId.isEmpty()){
            Division division = divisionService.getDivisionByName(divisiUser);
            Users pm = userService.getUserById(pmId);
            return projectRepository.findProjectAL(pm,directorateUser);
        }

        if(divisiUser.isEmpty() && !directorateUser.isEmpty() && pmId.isEmpty() && pmoId.isEmpty()){
            return projectRepository.findProjectAM(directorateUser);
        }
        return null;
    }


    @Override
    public List<Project> getProjectByPmID(String id) {
        return projectRepository.findProjectByPmId(id);
    }

    @Override
    public List<Project> getProjectByPmoID(String id) {
        return projectRepository.findProjectByPmoId(id);
    }

    @Override
    public List<Project> getProjectByCoPmID(String id) {
        return projectRepository.findProjectByCoPMId(id);
    }

    @Override
    public List<Project> getProjectByKeyword(String keyword) {
        return projectRepository.findProjectByKeyword(keyword);
    }
}
