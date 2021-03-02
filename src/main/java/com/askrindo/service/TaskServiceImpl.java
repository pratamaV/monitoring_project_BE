package com.askrindo.service;

import com.askrindo.entity.Project;
import com.askrindo.entity.Release;
import com.askrindo.entity.Task;
import com.askrindo.exception.DataNotFoundException;
import com.askrindo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ReleaseService releaseService;

    @Autowired
    ProjectService projectService;

    @Override
    public void saveTask(Task task) {
        List<Task> taskList = taskRepository.findTaskByReleaseId(task.getRelease().getId());
        Float totalScore = Float.valueOf(0);
        Float totalScore2 = Float.valueOf(0);
        for (Task task1: taskList) {
            totalScore = totalScore + task1.getScore();
        }
        totalScore2 = totalScore + task.getScore();
        task.setWeight(task.getScore()/totalScore2);
        for (Task task1: taskList) {
            task1.setWeight(task1.getScore()/totalScore2);
            taskRepository.save(task1);
        }
        taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(String id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public void updateTask(Task task) {
        if (!taskRepository.existsById(task.getId())) {
            throw new DataNotFoundException(String.format(DataNotFoundException.DATA_NOT_FOUND, task.getClass(), task.getId()));
        }
        taskRepository.save(task);

    }

    @Override
    public void updateTaskByReleaseId(Task task, String id) {
        if (!taskRepository.existsById(task.getId())) {
            throw new DataNotFoundException(String.format(DataNotFoundException.DATA_NOT_FOUND, task.getClass(), task.getId()));
        }
        if (task.getStatusDone().equalsIgnoreCase("Ya")){
            task.setTaskProsentase(task.getWeight());
            System.out.println(task.getTaskProsentase());
            taskRepository.save(task);
        }
        List<Task> taskList = taskRepository.findTaskByReleaseId(id);
        Float percentageRelease = Float.valueOf(0);
        for (Task task1: taskList) {
            percentageRelease = percentageRelease + task1.getTaskProsentase();
        }
        Release release = releaseService.getReleaseById(id);
        release.setProsentaseRelease(percentageRelease);
        releaseService.saveRelease(release);

        Project project = projectService.getProjectById(release.getProject().getId());
        List <Release> releaseList = releaseService.getReleaseByProjectId(release.getProject().getId());
        Float percentageProject = Float.valueOf(0);
        for (Release release1: releaseList) {
            percentageProject = percentageProject + (release1.getProsentaseRelease()*release1.getWeight());
            System.out.println(percentageProject);
        }
        project.setProsentaseProject(percentageProject);
        projectService.saveProject(project);
    }

    @Override
    public List<Task> getTaskByReleaseId(String id) {
        return taskRepository.findTaskByReleaseId(id);
    }

    @Override
    public void deleteAllTask() {
        taskRepository.deleteAll();
    }
}
