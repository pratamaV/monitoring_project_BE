package com.askrindo.service;

import com.askrindo.GlobalKey;
import com.askrindo.entity.Project;
import com.askrindo.entity.Release;
import com.askrindo.entity.sequence.SequenceIdRelease;
import com.askrindo.exception.DataNotFoundException;
import com.askrindo.repository.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    SequenceIdReleaseService sequenceIdReleaseService;

    @Autowired
    TaskService taskService;

    @Override
    public void saveRelease(Release release) {
        if (release.getId() == null){
            Project prjObj = projectService.getProjectById(release.getProject().getId());
//            SequenceIdRelease sequenceIdRelease = new SequenceIdRelease();
//            SequenceIdRelease idReleaseGen = sequenceIdReleaseService.saveSequenceIdRelease(sequenceIdRelease);
//            String releaseCodeGen = prjObj.getProjectCode()+"-"+idReleaseGen.getIdGeneratorRelease();
            String releaseCodeGen = this.generateReleaseCode(prjObj.getId());
            release.setReleaseCode(releaseCodeGen);
        }
        releaseRepository.save(release);

        List<Release> releaseList = releaseRepository.findReleaseByStatusReleaseAndProjectId("Active" , release.getProject().getId());
        Float totalScoreRelease = Float.valueOf(0);
        for (Release release1: releaseList) {
            totalScoreRelease = totalScoreRelease + release1.getScore();
        }
        for (Release release1: releaseList) {
            release1.setWeight(release1.getScore()/totalScoreRelease);
            releaseRepository.save(release1);
        }
    }

    public String generateReleaseCode(String idProject){
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
    public void updateStatusReleaseById(String id, String releaseStatus) {
        Release release = releaseRepository.findById(id).get();
        release.setStatusRelease(releaseStatus);
        if(releaseStatus.equalsIgnoreCase("Not Active")){
            release.setWeight(0.0f);
        }
        releaseRepository.save(release);

        List<Release> releaseList = releaseRepository.findReleaseByStatusReleaseAndProjectId("Active" , release.getProject().getId());
        Float totalScoreRelease = Float.valueOf(0);
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

    @Override
    public List<Release> getReleaseByProjectIdWithSort(String idProject, String orderBy, String sort) {
        if (sort.equals(GlobalKey.SORT_ASC)) {
            return releaseRepository.findAllByProjectId(idProject,Sort.by(Sort.Direction.ASC, orderBy));
        }
        else if (sort.equals(GlobalKey.SORT_DESC)){
            return releaseRepository.findAllByProjectId(idProject,Sort.by(Sort.Direction.DESC, orderBy));
        }
       return null;
    }

}
