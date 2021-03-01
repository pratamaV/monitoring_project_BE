package com.askrindo.service;

import com.askrindo.entity.Project;
import com.askrindo.entity.Task;
import com.askrindo.exception.DataNotFoundException;
import com.askrindo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void saveProject(Project project) {
        List<Project> projectList = projectRepository.findAll();
        Float totalScoreProject = Float.valueOf(0);
        Float totalScoreProject2 = Float.valueOf(0);
        for (Project project1: projectList) {
            totalScoreProject = totalScoreProject + project1.getScore();
        }
        totalScoreProject2 = totalScoreProject + project.getScore();
        project.setWeight(project.getScore()/totalScoreProject2);
        for (Project project1: projectList) {
            project1.setWeight(project1.getScore()/totalScoreProject2);
            projectRepository.save(project1);
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
//        if (!releaseRepository.existsById(release.getId())) {
//            throw new DataNotFoundException(String.format(DataNotFoundException.DATA_NOT_FOUND, release.getClass(), release.getId()));
//        }
//        List<Task> taskList = taskService.getAllTask();
//        Float percentageRelease = Float.valueOf(0);
//        for (Task task: taskList) {
//            percentageRelease = percentageRelease + task.getTaskProsentase();
//        }
//        release.setProsentaseRelease(percentageRelease);
        projectRepository.save(project);
    }
}
