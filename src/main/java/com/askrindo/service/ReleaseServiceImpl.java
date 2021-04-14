package com.askrindo.service;

import com.askrindo.GlobalKey;
import com.askrindo.entity.Project;
import com.askrindo.entity.Release;
import com.askrindo.entity.Users;
import com.askrindo.exception.DataNotFoundException;
import com.askrindo.repository.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by DELL on 26/02/2021.
 */

@Service
public class ReleaseServiceImpl implements ReleaseService {

    @Autowired
    ReleaseRepository releaseRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @Override
    public void saveRelease(Release release) {
        if (release.getId() == null) {
            Project prjObj = projectService.getProjectById(release.getProject().getId());
            String releaseCodeGen = this.generateReleaseCode(prjObj.getId());
            release.setReleaseCode(releaseCodeGen);
        }
        releaseRepository.save(release);
        updateContractedValueProject(release);
        updateWeightRelease(release);
    }

    public String generateReleaseCode(String idProject) {
        Project projectObj = projectService.getProjectById(idProject);
        Integer countReleaseByProjectId = releaseRepository.countReleaseByProjectId(idProject);
        String numberIncrement = String.format(Locale.getDefault(), "%04d", countReleaseByProjectId + 1);
        String releaseCode = projectObj.getProjectCode() + "-" + numberIncrement;
        return releaseCode;
    }

    @Override
    public List<Release> getAllRelease() {
        return releaseRepository.findAll();
    }

    @Override
    public Page<Release> getAllReleasePage(String projectName, String pmId, String pmoId, String copmId,  String status, String stage, String divisionId, String directoratUser, String developmentMode, Pageable pageable) {
        return releaseRepository.getAllReleaseByFilter(projectName, pmId, pmoId, copmId, status, stage, divisionId, directoratUser, developmentMode, pageable);
    }

    @Override
    public Release getReleaseById(String id) {
        if (!releaseRepository.existsById(id)) {
            throw new DataNotFoundException(String.format(DataNotFoundException.DATA_NOT_FOUND, releaseRepository.findById(id).get().getClass(), id));
        }
        return releaseRepository.findById(id).get();
    }

    @Override
    public List<Release> getReleaseByProjectId(String id) {
        return releaseRepository.findReleaseByProjectId(id);
    }

    @Override
    public void updateRelease(Release release) {
        releaseRepository.save(release);
        updateContractedValueProject(release);
    }

    private void updateContractedValueProject(Release release) {
        Project project = projectService.getProjectById(release.getProject().getId());
        List<Release> releaseList = releaseRepository.findReleaseByProjectId(project.getId());
        Float contractedValue = Float.valueOf(0.0f);
        if(!releaseList.isEmpty()){
            for (Release release1 : releaseList) {
                System.out.println(release1.getContractedValue()+"contractedvalue");
                if (!(release1.getContractedValue() == null)){
                    contractedValue = contractedValue + release1.getContractedValue();
                }
            }
            project.setContractedValue(contractedValue);
            projectService.saveProject(project);
        }

    }

    @Override
    public List<Release> getReleaseByStage(String stage) {
        return releaseRepository.findReleaseByStage(stage);
    }

    @Override
    public List<Release> getReleaseByStatus(String status) {
        return releaseRepository.findReleaseByStatus(status);
    }

    @Override
    public void updateStatusReleaseById(String id, String releaseStatus) {
        Release release = releaseRepository.findById(id).get();
        release.setStatusRelease(releaseStatus);
        if (releaseStatus.equalsIgnoreCase("Not Active")) {
            release.setWeight(0.0f);
        }
        releaseRepository.save(release);
        updateWeightRelease(release);
    }

    public void updateWeightRelease(Release release) {
        List<Release> releaseList = releaseRepository.findReleaseByStatusReleaseAndProjectId("Active", release.getProject().getId());
        Float totalScoreRelease = Float.valueOf(0);
        for (Release release1 : releaseList) {
            totalScoreRelease = totalScoreRelease + release1.getScore();
        }
        for (Release release1 : releaseList) {
            release1.setWeight(release1.getScore() / totalScoreRelease);
            releaseRepository.save(release1);
        }
    }

    @Override
    public List<Release> getReleaseByStatusReleaseAndProjectId(String statusRelease, String projectId) {
        return releaseRepository.findReleaseByStatusReleaseAndProjectId(statusRelease, projectId);
    }

    @Override
    public Page<Release> getReleaseByProjectId(String idProject, String pmId, String pmoId, String copmId,  String status, String stage, String divisionId, String directoratUser, String projectCode, String projectName, String developmentMode, Pageable pageable) {
        return releaseRepository.getAllReleaseByIdProject(idProject, pmId, pmoId, copmId, status, stage, divisionId, directoratUser, projectCode, projectName, developmentMode, pageable);
    }

    @Override
    public List<Release> getReleaseByProjectIdWithSort(String idProject, String orderBy, String sort, Integer page, Integer sizePerpage) {
        if (sort.equals(GlobalKey.SORT_ASC)) {
            Pageable paging = PageRequest.of(page, sizePerpage, Sort.by(Sort.Direction.ASC, orderBy));
            return releaseRepository.findAllByProjectId(idProject, paging);
        } else if (sort.equals(GlobalKey.SORT_DESC)) {
            Pageable paging = PageRequest.of(page, sizePerpage, Sort.by(Sort.Direction.DESC, orderBy));
            return releaseRepository.findAllByProjectId(idProject, paging);
        }
        return null;
    }

    @Override
    public Page<Release> getReleaseWithSort(String orderBy, String sort, Integer page, Integer sizePerPage) {
        if (sort.equals(GlobalKey.SORT_ASC)) {
            Pageable paging = PageRequest.of(page, sizePerPage, Sort.by(Sort.Direction.ASC, orderBy));
            return releaseRepository.findAll(paging);
        } else if (sort.equals(GlobalKey.SORT_DESC)) {
            Pageable paging = PageRequest.of(page, sizePerPage, Sort.by(Sort.Direction.DESC, orderBy));
            return releaseRepository.findAll(paging);
        }
        return null;
    }

    @Override
    public Page<Release> getAllReleaseSearch(String projectDependency, String projectName, Pageable pageable) {
        return releaseRepository.getAllReleaseSearch(projectDependency, projectName, pageable);
    }
}
