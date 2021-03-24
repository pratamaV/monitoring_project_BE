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

import java.util.*;

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
        for (Task task1: taskList2) {
            Release release = task1.getRelease();
            Float releaseWeight = task1.getRelease().getWeight();
            Float projectWeight = release.getProject().getWeight();
            Float taskWeight = task1.getWeight();
            userWeight = userWeight + (taskWeight*releaseWeight*projectWeight);

        }
        Users users = userService.getUserById(task.getAssignedTo().getId());
        users.setTotalWeight(userWeight);
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
            task.setTaskProsentase(1.0f);
            task.setStatusDone("Ya");
            task.setActEndDate(new Date());
            taskRepository.save(task);
        }
        List<Task> taskList = taskRepository.findTaskByReleaseId(id);
        Float percentageRelease = Float.valueOf(0);
        for (Task task1: taskList) {
            percentageRelease = percentageRelease + (task1.getTaskProsentase()*task1.getWeight());
        }
        Release release = releaseService.getReleaseById(id);
        release.setProsentaseRelease(percentageRelease);
        Task taskActEndDate = taskRepository.findTaskByActEndDateDesc();
        Task taskActStartDate = taskRepository.findTaskByActStartDateAcs();
        release.setActEnddate(taskActEndDate.getActEndDate());
        release.setActStartdate(taskActStartDate.getActStartDate());
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
        Float userPerformance = Float.valueOf(0);
        for (Task task1: taskList2) {
            Release release1 = task1.getRelease();
            Float releaseWeight = task1.getRelease().getWeight();
            Float projectWeight = release1.getProject().getWeight();
            Float taskWeight = task1.getWeight();
            userPerformance = userPerformance + (releaseWeight*projectWeight*task.getTaskProsentase()*taskWeight);
        }
        Users users = userService.getUserById(task.getAssignedTo().getId());
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
        Release releaseObj = releaseService.getReleaseById(task.getRelease().getId());
        SequenceIdTask sequenceIdTask = new SequenceIdTask();
        SequenceIdTask idTaskGen = sequenceIdTaskService.saveSequenceIdTask(sequenceIdTask);
        String releaseCodeGen = releaseObj.getReleaseCode()+"-"+idTaskGen.getIdGeneratorTask();
        task.setTaskCode(releaseCodeGen);
        task.setRelease(releaseObj);
        task.setActStartDate(task.getEstStartDate());
        task.setActEndDate(task.getEstEndDate());
        taskRepository.save(task);

        List<Task> taskList = taskRepository.findTaskByReleaseId(task.getRelease().getId());
        Float totalScore = Float.valueOf(0);
        Float percentageRelease = Float.valueOf(0);
        for (Task task1: taskList) {
            totalScore = totalScore + task1.getScore();
        }
        for (Task task1: taskList) {
            task1.setWeight(task1.getScore()/totalScore);
            percentageRelease = percentageRelease + (task1.getTaskProsentase()*task1.getWeight());
            taskRepository.save(task1);
        }

        Release release = releaseService.getReleaseById(task.getRelease().getId());
        release.setProsentaseRelease(percentageRelease);
        Task taskActEndDate = taskRepository.findTaskByActEndDateDesc();
        Task taskActStartDate = taskRepository.findTaskByActStartDateAcs();
        release.setActEnddate(taskActEndDate.getActEndDate());
        release.setActStartdate(taskActStartDate.getActStartDate());
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
            userPerformance = userPerformance + (releaseWeight*projectWeight*task.getTaskProsentase()*taskWeight);
        }
        Users user = userService.getUserById(task.getAssignedTo().getId());
        user.setTotalWeight(userWeight);
        user.setTotalPerformance(userPerformance);
        userService.saveUser(user);

        Task taskStartDate = taskRepository.findTaskByEstStartDateAcs();
        Task taskEndDate = taskRepository.findTaskByEstEndDateDesc();

        releaseObj.setEstStartdate(taskStartDate.getEstStartDate());
        releaseObj.setEstEnddate(taskEndDate.getEstEndDate());
        releaseService.saveRelease(releaseObj);

        for (Task task3: taskList) {
            Users users1 = userService.getUserById(task3.getAssignedTo().getId());
            List<Task> tasks = taskRepository.findTaskByAssignedToId(users1.getId());
            Float uwUpdate = Float.valueOf(0);
            Float performanceUpdate = Float.valueOf(0);
            for (Task task4: tasks) {
                Release release2 = task4.getRelease();
                Float releaseWeight = task4.getRelease().getWeight();
                Float projectWeight = release2.getProject().getWeight();
                Float taskWeight = task4.getWeight();
                uwUpdate = uwUpdate + (taskWeight*releaseWeight*projectWeight);
                performanceUpdate = performanceUpdate + (releaseWeight*projectWeight*task4.getTaskProsentase()*taskWeight);
            }
            System.out.println(users1);
            System.out.println(performanceUpdate);
            users1.setTotalPerformance(performanceUpdate);
            users1.setTotalWeight(uwUpdate);
            userService.saveUser(users1);
        }
    }

    @Override
    public void uploadDocumentById(String taskDocument, String id) {
        Task task = taskRepository.findById(id).get();
        task.setTaskDocument(taskDocument);
        taskRepository.save(task);
    }

    @Override
    public List<Task> getTaskByUserId(String id, String statusDone, String releaseName,
                                      String projectName, String estStartDate, String estEndDate) {
        return taskRepository.getTaskAssignedToId(id, statusDone, releaseName, projectName, estStartDate, estEndDate);
    }

    @Override
    public List<Task> getTaskByReleaseId(String id, String userId, String statusDone) {
        return taskRepository.getTaskByReleaseId(id, userId, statusDone);
    }

    @Override
    public List<Task> getTaskAfterDeadline() {
        List<Task> taskList = taskRepository.findAll();
        List<Task> taskDeadline = new ArrayList<>();
        taskDeadline.clear();
        for (Task task: taskList) {
            if (task.getEstEndDate().before(new Date()) && task.getStatusDone().equalsIgnoreCase("Tidak")){
                taskDeadline.add(task);
            }
        }
        return taskDeadline;
    }
}
