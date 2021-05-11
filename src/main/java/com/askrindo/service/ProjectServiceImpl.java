package com.askrindo.service;

import com.askrindo.GlobalKey;
import com.askrindo.dto.ProjectSearchDTO;
import com.askrindo.entity.*;
import com.askrindo.entity.sequence.SequenceIdProject;
import com.askrindo.repository.ProjectRepository;
import com.askrindo.spesification.ProjectSpesification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    TaskService taskService;


    @Autowired
    SequenceIdProjectService sequenceIdProjectService;

    @Override
    public void saveProject(Project project) {
        if (project.getId() == null) {
            SequenceIdProject sequenceIdProject = new SequenceIdProject();
            SequenceIdProject idProjectGen = sequenceIdProjectService.saveSequenceIdProject(sequenceIdProject);
            String idProjectgenFormat = idProjectGen.getIdGeneratorProject();
            project.setProjectCode(idProjectgenFormat);
        }
        projectRepository.save(project);
        List<Project> projectList = projectRepository.findProjectByStatusProject(GlobalKey.ACTIVE_STATUS);
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
    public List<Project> getProjectByLineItemBebanUsaha() {
        List<Project> projectList = projectRepository.findAll();
        List<Project> bebanUsaha = new ArrayList<>();
        bebanUsaha.clear();
        for (Project project : projectList) {
            if (project.getLineItem().equalsIgnoreCase("Beban Usaha")) {
                bebanUsaha.add(project);
            }
        }
        return bebanUsaha;
    }

    @Override
    public List<Project> getProjectByLineItemBelanjaModal() {
        List<Project> projectList = projectRepository.findAll();
        List<Project> belanjaModal = new ArrayList<>();
        belanjaModal.clear();
        for (Project project : projectList) {
            if (project.getLineItem().equalsIgnoreCase("Belanja Modal/ Software") || project.getLineItem().equalsIgnoreCase("Belanja Modal/ Hardware")) {
                belanjaModal.add(project);
            }
        }
        return belanjaModal;
    }

    @Override
    public List<Project> getAllProjectList() {
        return projectRepository.findAll();
    }

//    @Override
//    public Page<Project> getAllProjectPageFilter(String projectDependency, Pageable pageable) {
//        return projectRepository.getAllProjectDependency(projectDependency, pageable);
//    }

    @Override
    public Page<Project> getAllProjectPageFilter(String projectDependency, String projectName, Integer page, Integer sizePerpage, String orderBy , String sort ) {
        if (sort.equals(GlobalKey.SORT_ASC)) {
            Pageable paging = PageRequest.of(page, sizePerpage, Sort.by(Sort.Direction.ASC, orderBy));
            return projectRepository.getAllProjectDependency(projectDependency, projectName, paging);
        } else if (sort.equals(GlobalKey.SORT_DESC)) {
            Pageable paging = PageRequest.of(page, sizePerpage, Sort.by(Sort.Direction.DESC, orderBy));
            return projectRepository.getAllProjectDependency(projectDependency, projectName, paging);
        }
        return null;
//        return projectRepository.getAllProjectDependency(projectDependency, pageable);
    }

    @Override
    public Page<Project> getProjectByCoPMId(String id, String projectDependency, String projectName, Pageable pageable ) {
        return projectRepository.findProjectBycoPMId(id, projectDependency, projectName, pageable);
    }


//    @Override
//    public Page<Project> getAllProjectWithFilter(String divisionId,
//                                                 String pmId,
//                                                 String pmoId,
//                                                 String statusProject,
//                                                 String directoratUser, Pageable pageable) {
//        return projectRepository.getAllProject(divisionId, pmId, pmoId, statusProject, directoratUser, pageable);
//    }

    @Override
    public Page<Project> getAllProjectWithSort(String orderBy, String sort, Integer page, Integer sizePerpage) {

        if (sort.equals(GlobalKey.SORT_ASC)){
            Pageable paging = PageRequest.of(page, sizePerpage, Sort.by(Sort.Direction.ASC, orderBy));
//            Page<Project> pagedResult = projectRepository.findAll(paging);
            return projectRepository.findAll(paging);
        }
        else if (sort.equals(GlobalKey.SORT_DESC)){
            Pageable paging = PageRequest.of(page, sizePerpage, Sort.by(Sort.Direction.DESC, orderBy));
//            Page<Project> pagedResult = projectRepository.findAll(paging);
            return projectRepository.findAll(paging);
        }
        return null;
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
        if (projectStatus.equalsIgnoreCase(GlobalKey.NOT_ACTIVE_STATUS)) {
            project.setWeight(0.0f);
        }
        projectRepository.save(project);
        List<Project> projectList = projectRepository.findProjectByStatusProject(GlobalKey.ACTIVE_STATUS);
        Float totalScoreProject = Float.valueOf(0);
        for (Project project1 : projectList) {
            totalScoreProject = totalScoreProject + project1.getScore();
        }
        for (Project project1 : projectList) {
            project1.setWeight(project1.getScore() / totalScoreProject);
            projectRepository.save(project1);
        }
        List<Release> releaseList = releaseService.getReleaseByProjectId(project.getId());
        if (!releaseList.isEmpty()) {
            for (Release release : releaseList) {
                release.setStatusRelease(GlobalKey.NOT_ACTIVE_STATUS);
                release.setWeight(0.0f);
                releaseService.saveRelease(release);
                List<Task> taskList = taskService.getTaskByReleaseId(release.getId());
                if (!taskList.isEmpty()) {
                    for (Task task : taskList) {
                        task.setWeight(Float.valueOf(0));
                        Task taskObj = taskService.saveTaskOrdinary(task);
                        taskService.updatePerformanceUser(taskObj, release, project);
                    }
                }
                taskService.updateProsentaseRelease(release);
                taskService.updateProsentaseProject(project);
            }
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

//    @Override
//    public List<Project> getProjectByDivisiUserAndStatusProject(String divisiUser, String statusProject) {
//        return projectRepository.findProjectByDivisiUserAndStatusProject(divisiUser, "Active");
//    }

    @Override
    public Page<Project> getProjects(Pageable pageable, ProjectSearchDTO projectSearchDTO) {
        Specification<Project> projectSpecification = ProjectSpesification.getSpesification(projectSearchDTO);
        return projectRepository.findAll(projectSpecification, pageable);
    }

//    @Override
//    public List<Project> getProjectList(String divisiUser, String directorateUser, String pmId, String pmoId) {
//        if (divisiUser.isEmpty() && !directorateUser.isEmpty() && !pmId.isEmpty() && !pmoId.isEmpty()) {
//            System.out.println("kosong");
//            Users pm = userService.getUserById(pmId);
//            Users pmo = userService.getUserById(pmoId);
//            return projectRepository.findProjectAB(pm, pmo, directorateUser);
//        }
//
//        if (divisiUser.isEmpty() && directorateUser.isEmpty() && !pmId.isEmpty() && !pmoId.isEmpty()) {
//            Users pm = userService.getUserById(pmId);
//            Users pmo = userService.getUserById(pmoId);
//            return projectRepository.findProjectAC(pm, pmo);
//        }
//
//        if (divisiUser.isEmpty() && directorateUser.isEmpty() && pmId.isEmpty() && !pmoId.isEmpty()) {
//            Users pmo = userService.getUserById(pmoId);
//            return projectRepository.findProjectAD(pmo);
//        }
//
//        if (divisiUser.isEmpty() && directorateUser.isEmpty() && !pmId.isEmpty() && pmoId.isEmpty()) {
//            Users pm = userService.getUserById(pmId);
//            return projectRepository.findProjectAE(pm);
//        }
//
//        if (!divisiUser.isEmpty() && !directorateUser.isEmpty() && !pmId.isEmpty() && !pmoId.isEmpty()) {
//            System.out.println("masuk sini");
//            Division division = divisionService.getDivisionByName(divisiUser);
//            Users pm = userService.getUserById(pmId);
//            Users pmo = userService.getUserById(pmoId);
//            return projectRepository.findProjectAA(division, pm, pmo, directorateUser);
//        }
//
//        if (!divisiUser.isEmpty() && !directorateUser.isEmpty() && pmId.isEmpty() && !pmoId.isEmpty()) {
//            System.out.println("masuk sini");
//            Division division = divisionService.getDivisionByName(divisiUser);
//            Users pmo = userService.getUserById(pmoId);
//            return projectRepository.findProjectAI(division, pmo, directorateUser);
//        }
//
//        if (!divisiUser.isEmpty() && !directorateUser.isEmpty() && !pmId.isEmpty() && pmoId.isEmpty()) {
//            System.out.println("masuk sini dia yo");
//            Division division = divisionService.getDivisionByName(divisiUser);
//            Users pm = userService.getUserById(pmId);
//            System.out.println("pm nya adalah: " + pm);
//            System.out.println("direktorateuser: " + directorateUser);
//            System.out.println("divisionnyaUser: " + division);
//            return projectRepository.findProjectAF(division, pm, directorateUser);
//        }
//
//        if (!divisiUser.isEmpty() && directorateUser.isEmpty() && pmId.isEmpty() && pmoId.isEmpty()) {
//            System.out.println("divisi doang disini");
//            Division division = divisionService.getDivisionByName(divisiUser);
//            return projectRepository.findProjectAG(division);
//        }
//
//        if (!divisiUser.isEmpty() && !directorateUser.isEmpty() && pmId.isEmpty() && pmoId.isEmpty()) {
//            Division division = divisionService.getDivisionByName(divisiUser);
//            return projectRepository.findProjectAH(division, directorateUser);
//        }
//
//        if (!divisiUser.isEmpty() && directorateUser.isEmpty() && !pmId.isEmpty() && pmoId.isEmpty()) {
//            Division division = divisionService.getDivisionByName(divisiUser);
//            Users pm = userService.getUserById(pmId);
//            return projectRepository.findProjectAJ(division, pm);
//        }
//
//        if (divisiUser.isEmpty() && !directorateUser.isEmpty() && pmId.isEmpty() && !pmoId.isEmpty()) {
//            Division division = divisionService.getDivisionByName(divisiUser);
//            Users pmo = userService.getUserById(pmoId);
//            return projectRepository.findProjectAK(pmo, directorateUser);
//        }
//
//        if (divisiUser.isEmpty() && !directorateUser.isEmpty() && !pmId.isEmpty() && pmoId.isEmpty()) {
//            Division division = divisionService.getDivisionByName(divisiUser);
//            Users pm = userService.getUserById(pmId);
//            return projectRepository.findProjectAL(pm, directorateUser);
//        }
//
//        if (divisiUser.isEmpty() && !directorateUser.isEmpty() && pmId.isEmpty() && pmoId.isEmpty()) {
//            return projectRepository.findProjectAM(directorateUser);
//        }
//        return null;
//    }


//    @Override
//    public List<Project> getProjectByPmID(String id) {
//        return projectRepository.findProjectByPmId(id);
//    }
//
//    @Override
//    public List<Project> getProjectByPmoID(String id) {
//        return projectRepository.findProjectByPmoId(id);
//    }
//
//    @Override
//    public List<Project> getProjectByCoPmID(String id) {
//        return projectRepository.findProjectByCoPMId(id);
//    }


//    @Override
//    public List<Project> getProjectByKeyword(String keyword) {
//        return projectRepository.findProjectByKeyword(keyword);
//    }


    @Override
    public List<Project> getProjectByDirectorateUser(String directorateUser) {
        return projectRepository.findProjectByDirectorateUser(directorateUser);
    }
}
