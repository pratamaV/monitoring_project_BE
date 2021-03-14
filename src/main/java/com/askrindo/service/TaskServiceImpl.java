package com.askrindo.service;

import com.askrindo.entity.Project;
import com.askrindo.entity.Release;
import com.askrindo.entity.Task;
import com.askrindo.entity.sequence.SequenceIdTask;

import com.askrindo.entity.Users;
import com.askrindo.exception.DataNotFoundException;
import com.askrindo.repository.TaskRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    UserService userService;

    @Autowired
    SequenceIdTaskService sequenceIdTaskService;

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
        Release releaseObj = releaseService.getReleaseById(task.getRelease().getId());
        SequenceIdTask sequenceIdTask = new SequenceIdTask();
        SequenceIdTask idTaskGen = sequenceIdTaskService.saveSequenceIdTask(sequenceIdTask);
        String releaseCodeGen = releaseObj.getReleaseCode()+"-"+idTaskGen.getIdGeneratorTask();
        task.setTaskCode(releaseCodeGen);
        task.setRelease(releaseObj);
        taskRepository.save(task);

        List<Task> taskList2 = taskRepository.findTaskByAssignedToId(task.getAssignedTo().getId());
        Float userWeight = Float.valueOf(0);
        Float userPerformance = Float.valueOf(0);
        for (Task task1: taskList2) {
            Release release = task1.getRelease();
            Float releaseWeight = task1.getRelease().getWeight();
            Float projectWeight = release.getProject().getWeight();
            Float taskWeight = task1.getWeight();
            userWeight = userWeight + (taskWeight*releaseWeight*projectWeight);
            userPerformance = userPerformance + (taskWeight*releaseWeight*projectWeight*task.getTaskProsentase());
        }
        Users users = userService.getUserById(task.getAssignedTo().getId());
        users.setTotalWeight(userWeight);
        users.setTotalPerformance(userPerformance);
        userService.saveUser(users);
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
            task.setStatusDone("Ya");
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
        }
        project.setProsentaseProject(percentageProject);
        projectService.saveProject(project);

        List<Task> taskList2 = taskRepository.findTaskByAssignedToId(task.getAssignedTo().getId());
        Float userWeight = Float.valueOf(0);
        Float userPerformance = Float.valueOf(0);
        for (Task task1: taskList2) {
            Release release1 = task1.getRelease();
            Float releaseWeight = task1.getRelease().getWeight();
            Float projectWeight = release1.getProject().getWeight();
            Float taskWeight = task1.getWeight();
            userWeight = userWeight + (taskWeight*releaseWeight*projectWeight);
            userPerformance = userPerformance + (taskWeight*releaseWeight*projectWeight*task.getTaskProsentase());
        }
        Users users = userService.getUserById(task.getAssignedTo().getId());
        users.setTotalWeight(userWeight);
        users.setTotalPerformance(userPerformance);
        userService.saveUser(users);
    }

    @Override
    public List<Task> getTaskByReleaseId(String id) {
        return taskRepository.findTaskByReleaseId(id);
    }

    @Override
    public void deleteAllTask() {
        taskRepository.deleteAll();
    }

    @Override
    public Float getWeightTaskByUserId(String userID) {
        List<Task> taskList = taskRepository.findTaskByAssignedToId(userID);
        Float userWeight = Float.valueOf(0);
        Float userPerformance = Float.valueOf(0);
        for (Task task: taskList) {
            Release release = task.getRelease();
            Float releaseWeight = task.getRelease().getWeight();
            Float projectWeight = release.getProject().getWeight();
            Float taskWeight = task.getWeight();
            userWeight = userWeight + (taskWeight*releaseWeight*projectWeight);
            userPerformance = userPerformance + (taskWeight*releaseWeight*projectWeight*task.getTaskProsentase());
        }
        Users users = userService.getUserById(userID);
        users.setTotalWeight(userWeight);
        users.setTotalPerformance(userPerformance);
        userService.saveUser(users);
        return userWeight;
    }

    @Override
    public void addTask(Task task) {
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
        Release releaseObj = releaseService.getReleaseById(task.getRelease().getId());
        SequenceIdTask sequenceIdTask = new SequenceIdTask();
        SequenceIdTask idTaskGen = sequenceIdTaskService.saveSequenceIdTask(sequenceIdTask);
        String releaseCodeGen = releaseObj.getReleaseCode()+"-"+idTaskGen.getIdGeneratorTask();
        task.setTaskCode(releaseCodeGen);
        task.setRelease(releaseObj);
        taskRepository.save(task);

        List<Task> taskList2 = taskRepository.findTaskByAssignedToId(task.getAssignedTo().getId());
        Float userWeight = Float.valueOf(0);
        Float userPerformance = Float.valueOf(0);
        for (Task task1: taskList2) {
            Release release = task1.getRelease();
            Float releaseWeight = task1.getRelease().getWeight();
            Float projectWeight = release.getProject().getWeight();
            Float taskWeight = task1.getWeight();
            userWeight = userWeight + (taskWeight*releaseWeight*projectWeight);
            userPerformance = userPerformance + (taskWeight*releaseWeight*projectWeight*task.getTaskProsentase());
        }
        Users user = userService.getUserById(task.getAssignedTo().getId());
        user.setTotalWeight(userWeight);
        user.setTotalPerformance(userPerformance);
        userService.saveUser(user);
    }

    @Override
    public void uploadDocumentById(String taskDocument, String id) {
        Task task = taskRepository.findById(id).get();
        task.setTaskDocument(taskDocument);
        taskRepository.save(task);
    }

    @Override
    public List<Task> getTaskByUserId(String id) {
        return taskRepository.findTaskByAssignedToId(id);
    }

    @Override
    public List<Task> getTaskByReleaseId(String id, String userId, String statusDone) {
        return taskRepository.getTaskByReleaseId(id, userId, statusDone);
    }
}
