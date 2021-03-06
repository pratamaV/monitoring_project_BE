package com.askrindo.service;

import com.askrindo.entity.Release;

import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */
public interface ReleaseService {
    public void saveRelease(Release release);
    public List<Release> getAllRelease();
    public Release getReleaseById(String id);
    public void updateRelease(Release release);
    public List<Release> getReleaseByProjectId(String id);
    public List<Release> getReleaseByStage(String stage);
    public List<Release> getReleaseByStatus(String status);
    public void updateStatusReleaseById(String id);
    public List<Release> getReleaseByProjectId(String idProject, String status, String stage);
}
