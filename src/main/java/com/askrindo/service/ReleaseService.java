package com.askrindo.service;

import com.askrindo.entity.Project;
import com.askrindo.entity.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */
public interface ReleaseService {
    public void saveRelease(Release release);
    public List<Release> getAllRelease();
    public Page<Release> getAllReleasePage(String projectName, String pmId, String pmoId, String copmId,  String status, String stage, String divisionId, String directoratUser, String developmentMode, Pageable pageable);
    public Release getReleaseById(String id);
    public void updateRelease(Release release);
    public List<Release> getReleaseByProjectId(String id);
    public List<Release> getReleaseByStage(String stage);
    public List<Release> getReleaseByStatus(String status);
    public void updateStatusReleaseById(String id, String releaseStatus);
    public Page<Release> getReleaseByProjectId(String idProject, String pmId, String pmoId, String copmId,  String status, String stage, String divisionId, String directoratUser, String projectCode, String projectName, String developmentMode, Pageable pageable);
    public List<Release> getReleaseByProjectIdWithSort(String idProject, String orderBy, String sort, Integer page, Integer pagePerSize);
    public Page<Release> getReleaseWithSort(String orderBy, String sort, Integer page, Integer sizePerPage);
    public List<Release> getReleaseByStatusReleaseAndProjectId(String statusRelease, String projectId);
    public Page<Release> getAllReleaseSearch(String projectDependency, String projectName, Pageable pageable);
}
