package com.askrindo.service;

import com.askrindo.entity.Project;
import com.askrindo.entity.Release;
import com.askrindo.entity.sequence.SequenceIdRelease;
import com.askrindo.exception.DataNotFoundException;
import com.askrindo.repository.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    SequenceIdReleaseService sequenceIdReleaseService;

    @Autowired
    TaskService taskService;

    @Override
    public void saveRelease(Release release) {
        List<Release> releaseList = releaseRepository.findReleaseByStatusReleaseAndProjectId("aktif" , release.getProject().getId());
        Float totalScoreRelease = Float.valueOf(0);
        Float totalScoreRelease2 = Float.valueOf(0);
        for (Release release1: releaseList) {
            totalScoreRelease = totalScoreRelease + release1.getScore();
        }
        totalScoreRelease2 = totalScoreRelease + release.getScore();
        release.setWeight(release.getScore()/totalScoreRelease2);
        for (Release release1: releaseList) {
            release1.setWeight(release1.getScore()/totalScoreRelease2);
            releaseRepository.save(release1);
        }

        if (release.getId() == null){
            Project prjObj = projectService.getProjectById(release.getProject().getId());
            SequenceIdRelease sequenceIdRelease = new SequenceIdRelease();
            SequenceIdRelease idReleaseGen = sequenceIdReleaseService.saveSequenceIdRelease(sequenceIdRelease);
            String releaseCodeGen = prjObj.getProjectCode()+"-"+idReleaseGen.getIdGeneratorRelease();
            release.setReleaseCode(releaseCodeGen);
        }
        releaseRepository.save(release);
    }

    @Override
    public List<Release> getAllRelease() {
        return releaseRepository.findAll();
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
    public void updateStatusReleaseById(String id) {
        Release release = releaseRepository.findById(id).get();
        if(release.getStatusRelease().equalsIgnoreCase("aktif")){
            release.setStatusRelease("tidak aktif");
            releaseRepository.save(release);
        } else {
            release.setStatusRelease("aktif");
            releaseRepository.save(release);
        }

        List<Release> releaseList = releaseRepository.findReleaseByStatusReleaseAndProjectId("aktif" , release.getProject().getId());
        Float totalScoreRelease = Float.valueOf(0);
        Float totalScoreRelease2 = Float.valueOf(0);
        for (Release release1: releaseList) {
            totalScoreRelease = totalScoreRelease + release1.getScore();
        }
        for (Release release1: releaseList) {
            release1.setWeight(release1.getScore()/totalScoreRelease);
            releaseRepository.save(release1);
        }
    }

    @Override
    public List<Release> getReleaseByProjectId(String idProject, String status, String stage) {
        return releaseRepository.getReleasebyId2(idProject, status, stage);
    }
}
