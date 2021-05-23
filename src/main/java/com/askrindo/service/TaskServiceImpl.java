package com.askrindo.service;

import com.askrindo.GlobalKey;
import com.askrindo.entity.*;

import com.askrindo.exception.DataNotFoundException;
import com.askrindo.repository.TaskRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
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
    FileService fileService;

    @Value("${document-task}")
    String documentTask;

    @Override
    public Task saveTaskOrdinary(Task task) {
        task.setActStartDate(task.getEstStartDate());
        task.setActEndDate(task.getEstEndDate());
        return taskRepository.save(task);
    }

    @Override
    public void saveTask(Task task) {
        task.setWeight(calculateWeightTask(task));
        Release releaseObj = releaseService.getReleaseById(task.getRelease().getId());
        String idTaskGen = this.generateTaskCode(releaseObj.getId());
        task.setActStartDate(task.getEstStartDate());
        task.setActEndDate(task.getEstEndDate());
        task.setTaskCode(idTaskGen);
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

    private Float calculateWeightTask(Task task) {
        List<Task> taskList = taskRepository.findTaskByReleaseId(task.getRelease().getId());
        Float totalScore = Float.valueOf(0);
        Float totalScore2 = Float.valueOf(0);
        Float weightTaskUpd = Float.valueOf(0);
        for (Task task1: taskList) {
            totalScore = totalScore + task1.getScore();
        }
        totalScore2 = totalScore + task.getScore();
        weightTaskUpd = task.getScore()/totalScore;
        for (Task task1: taskList) {
            task1.setWeight(task1.getScore()/totalScore);
            taskRepository.save(task1);
        }
        return weightTaskUpd;
    }

    //    @Override
    public String generateTaskCode(String idRelease){
        Release releaseObj = releaseService.getReleaseById(idRelease);
        Integer countTaskByReleaseId = taskRepository.countTaskByReleaseId(idRelease);
        String numberIncrement = String.format(Locale.getDefault(), "%05d", countTaskByReleaseId + 1);
        String taskCode = releaseObj.getReleaseCode() + "-" + numberIncrement;
        return taskCode;
    }

    public String generateFileCode(String idTask){
        Task taskObj = taskRepository.findById(idTask).get();
        Integer countTaskByReleaseId = fileService.countFileByIdTask(idTask);
        String numberIncrement = String.format(Locale.getDefault(), "%01d", countTaskByReleaseId + 1);
        String fileCode = taskObj.getTaskCode() + "-" + numberIncrement;
        return fileCode;
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
        task.setTaskProsentase(Float.valueOf(task.getTaskProsentase()));
        if (task.getTaskProsentase() == 0) {
            task.setStatusDone(GlobalKey.TASK_STATUS_NOT_STARTED);
        } else if (task.getTaskProsentase() > 0 && task.getTaskProsentase() < 1) {
            task.setStatusDone(GlobalKey.TASK_STATUS_ON_PROGRESS);
        } else if (task.getTaskProsentase() == 1) {
            task.setStatusDone(GlobalKey.TASK_STATUS_DONE);
        }
        taskRepository.save(task);
        task.setWeight(calculateWeightTask(task));
        Task taskObj = taskRepository.save(task);
        Release releaseObj = releaseService.getReleaseById(taskObj.getRelease().getId());
        Project projectObj = projectService.getProjectById(releaseObj.getProject().getId());
        updateProsentaseRelease(releaseObj);
        updateProsentaseProject(projectObj);
        updatePerformanceUser(taskObj, releaseObj, projectObj);
        Task taskActEndDate = taskRepository.findTaskByActEndDateDesc(task.getRelease().getId());
        Task taskActStartDate = taskRepository.findTaskByActStartDateAcs(task.getRelease().getId());
        Task taskPlanStartDate = taskRepository.findTaskByEstStartDateAcs(task.getRelease().getId());
        Task taskPlanEndDate = taskRepository.findTaskByEstEndDateDesc(task.getRelease().getId());
        releaseObj.setActEnddate(taskActEndDate.getActEndDate());
        releaseObj.setActStartdate(taskActStartDate.getActStartDate());
        releaseObj.setEstEnddate(taskPlanEndDate.getEstEndDate());
        releaseObj.setEstStartdate(taskPlanStartDate.getEstStartDate());
        releaseObj.setStatusRelease(GlobalKey.ACTIVE_STATUS);
        releaseService.saveRelease(releaseObj);
        projectObj.setStatusProject(GlobalKey.ACTIVE_STATUS);
        projectService.saveProject(projectObj);
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
        Task taskActEndDate = taskRepository.findTaskByActEndDateDesc(task.getRelease().getId());
        Task taskActStartDate = taskRepository.findTaskByActStartDateAcs(task.getRelease().getId());
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
        String taskCodeGen = this.generateTaskCode(releaseObj.getId());
        task.setTaskCode(taskCodeGen);
        task.setRelease(releaseObj);
        task.setActStartDate(task.getEstStartDate());
        task.setActEndDate(task.getEstEndDate());
        taskRepository.save(task);

        List<Task> taskList = taskRepository.findTaskByReleaseId(task.getRelease().getId());
        Float totalScore = Float.valueOf(0);
        Float percentageRelease = Float.valueOf(0);
        if(!taskList.isEmpty()){
            for (Task task1: taskList) {
                totalScore = totalScore + task1.getScore();
            }
            for (Task task1: taskList) {
                task1.setWeight(task1.getScore()/totalScore);
                percentageRelease = percentageRelease + (task1.getTaskProsentase()*task1.getWeight());
                taskRepository.save(task1);
            }
        }
        Release release = releaseService.getReleaseById(task.getRelease().getId());
        release.setProsentaseRelease(percentageRelease);
        Task taskActEndDate = taskRepository.findTaskByActEndDateDesc(task.getRelease().getId());
        Task taskActStartDate = taskRepository.findTaskByActStartDateAcs(task.getRelease().getId());
        release.setActEnddate(taskActEndDate.getActEndDate());
        release.setActStartdate(taskActStartDate.getActStartDate());
        releaseService.saveRelease(release);

        Project project = projectService.getProjectById(release.getProject().getId());
        List <Release> releaseList = releaseService.getReleaseByProjectId(release.getProject().getId());
        Float percentageProject = Float.valueOf(0);
        if (!releaseList.isEmpty()){
            for (Release release1: releaseList) {
                percentageProject = percentageProject + (release1.getProsentaseRelease()*release1.getWeight());
            }
            project.setProsentaseProject(percentageProject);
            projectService.saveProject(project);
        }

        List<Task> taskList2 = taskRepository.findTaskByAssignedToId(task.getAssignedTo().getId());
        Float userWeight = Float.valueOf(0);
        Float userPerformance = Float.valueOf(0);
        if (!taskList2.isEmpty()){
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
            Task taskStartDate = taskRepository.findTaskByEstStartDateAcs(task.getRelease().getId());
            Task taskEndDate = taskRepository.findTaskByEstEndDateDesc(task.getRelease().getId());

            releaseObj.setEstStartdate(taskStartDate.getEstStartDate());
            releaseObj.setEstEnddate(taskEndDate.getEstEndDate());
            releaseService.saveRelease(releaseObj);
        }

        if(!taskList.isEmpty()) {
            for (Task task3 : taskList) {
                Users users1 = userService.getUserById(task3.getAssignedTo().getId());
                List<Task> tasks = taskRepository.findTaskByAssignedToId(users1.getId());
                Float uwUpdate = Float.valueOf(0);
                Float performanceUpdate = Float.valueOf(0);
                for (Task task4 : tasks) {
                    Release release2 = releaseService.getReleaseById(task4.getRelease().getId());
                    Float releaseWeight = release2.getWeight();
                    Project project1 = projectService.getProjectById(release2.getProject().getId());
                    Float projectWeight = project1.getWeight();
                    Float taskWeight = task4.getWeight();
                    uwUpdate = uwUpdate + (taskWeight * releaseWeight * projectWeight);
                    performanceUpdate = performanceUpdate + (releaseWeight * projectWeight * task4.getTaskProsentase() * taskWeight);
                }
                users1.setTotalPerformance(performanceUpdate);
                users1.setTotalWeight(uwUpdate);
                userService.saveUser(users1);
            }
        }
    }

//    @Override
//    public void uploadDocumentById(String taskDocument, String id) {
//        Task task = taskRepository.findById(id).get();
//        task.setTaskDocument(taskDocument);
//        taskRepository.save(task);
//    }

    @Override
    public void uploadDocumentById(MultipartFile taskDoc, File file, String taskId) {
        Task task = taskRepository.findById(taskId).get();
        String fileCode = this.generateFileCode(task.getId());
        String taskDocument = StringUtils.cleanPath(fileCode+"." + FilenameUtils.getExtension(taskDoc.getOriginalFilename()));

        try {
            if (taskDoc != null) {
                taskDoc.transferTo(Paths.get(documentTask, taskDocument));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.setDocumentCode(fileCode);
        file.setDocumentName(taskDocument);
        file.setTask(task);
        file.setCreatedDate(new Date());
        fileService.saveFile(file);
    }

    @Override
    public Page<Task> getTaskByUserId(String id, String statusDone, String releaseName,
                                      String projectName,
                                      Date estStartDateFrom,
                                      Date estStartDateTo,
                                      Date estEndDateFrom,
                                      Date estEndDateTo, Pageable pageable) {
        Date estStartDateToNow = new Date();
        Date estEndDateToNow = new Date();

        if (estStartDateFrom != null && estEndDateFrom == null) {
            if (estStartDateTo != null) {
                return taskRepository.getTaskAssignedToIdWithEstStartDate(id, statusDone, releaseName, projectName, estStartDateFrom, estStartDateTo, pageable);
            } else if (estStartDateTo == null) {
                return taskRepository.getTaskAssignedToIdWithEstStartDate(id, statusDone, releaseName, projectName, estStartDateFrom, estStartDateToNow, pageable);
            }
        } else if (estStartDateFrom == null && estEndDateFrom != null) {
            if (estEndDateTo != null) {
                return taskRepository.getTaskAssignedToIdWithEstEndDate(id, statusDone, releaseName, projectName, estEndDateFrom, estEndDateTo, pageable);
            } else if (estEndDateTo == null) {
                return taskRepository.getTaskAssignedToIdWithEstEndDate(id, statusDone, releaseName, projectName, estEndDateFrom, estEndDateToNow, pageable);
            }
        } else if (estStartDateFrom != null && estEndDateFrom != null) {
            if (estStartDateTo == null && estEndDateTo == null) {
                return taskRepository.getTaskAssignedToIdWithDate(id, statusDone, releaseName, projectName, estStartDateFrom, estStartDateToNow, estEndDateFrom, estEndDateToNow, pageable);
            } else if (estStartDateTo == null) {
                return taskRepository.getTaskAssignedToIdWithDate(id, statusDone, releaseName, projectName, estStartDateFrom, estStartDateToNow, estEndDateFrom, estEndDateTo, pageable);
            } else if (estEndDateTo == null) {
                return taskRepository.getTaskAssignedToIdWithDate(id, statusDone, releaseName, projectName, estStartDateFrom, estStartDateTo, estEndDateFrom, estEndDateToNow, pageable);
            }
            else {
                return taskRepository.getTaskAssignedToIdWithDate(id, statusDone, releaseName, projectName, estStartDateFrom, estStartDateTo, estEndDateFrom, estEndDateTo, pageable);
            }
        } else if (estStartDateFrom == null && estEndDateFrom == null) {
            return taskRepository.getTaskAssignedToId(id, statusDone, releaseName, projectName, pageable);
        }
        return null;
    }

    @Override
    public Page<Task> getTaskByReleaseId(String id, String userId, String statusDone, Pageable pageable) {
        return taskRepository.getTaskByReleaseId(id, userId, statusDone, pageable);
    }

    @Override
    public Page<Task> getTaskByReleaseIdWithSort(String idRelease, String orderBy, String sort, Integer page, Integer sizePerpage) {
        if (sort.equals(GlobalKey.SORT_ASC)) {
            Pageable paging = PageRequest.of(page, sizePerpage, Sort.by(Sort.Direction.ASC, orderBy));
            return taskRepository.findAllByReleaseId(idRelease, paging);
        }
        else if (sort.equals(GlobalKey.SORT_DESC)){
            Pageable paging = PageRequest.of(page, sizePerpage, Sort.by(Sort.Direction.DESC, orderBy));
            return taskRepository.findAllByReleaseId(idRelease, paging);
        }
        return null;
    }

    @Override
    public Page<Task> getTaskByUserIdWithSort(String idUser, String orderBy, String sort, Integer page, Integer sizePerpage) {
        if (sort.equals(GlobalKey.SORT_ASC)) {
            Pageable paging = PageRequest.of(page, sizePerpage, Sort.by(Sort.Direction.ASC, orderBy));
            return taskRepository.findAllByAssignedToId(idUser, paging);
        }
        else if (sort.equals(GlobalKey.SORT_DESC)){
            Pageable paging = PageRequest.of(page, sizePerpage, Sort.by(Sort.Direction.DESC, orderBy));
            return taskRepository.findAllByAssignedToId(idUser, paging);
        }
        return null;
    }

    @Override
    public List<Task> getTaskAfterDeadline() {
        return taskRepository.findTaskDeadline();
    }

    @Override
    public void updateDoneTask(String idTask, Float prosentase) {
        Task taskObj = this.getTaskById(idTask);
        Release releaseObj = releaseService.getReleaseById(taskObj.getRelease().getId());
        Project projectObj = projectService.getProjectById(releaseObj.getProject().getId());

        taskObj.setTaskProsentase(Float.valueOf(prosentase));
        if (prosentase == 0) {
            taskObj.setStatusDone(GlobalKey.TASK_STATUS_NOT_STARTED);
        } else if (prosentase > 0 && prosentase < 1) {
            taskObj.setStatusDone(GlobalKey.TASK_STATUS_ON_PROGRESS);
        } else if (prosentase == 1) {
            taskObj.setStatusDone(GlobalKey.TASK_STATUS_DONE);
        }
        taskRepository.save(taskObj);
        updateProsentaseRelease(releaseObj);
        updateProsentaseProject(projectObj);
        updatePerformanceUser(taskObj, releaseObj, projectObj);
    }

    @Override
    public void deleteTaskFile(String idFile) {
        File fileObj = fileService.getFileById(idFile);
        java.io.File fileToDelete = new java.io.File(documentTask+fileObj.getDocumentName());
        fileToDelete.delete();
        fileService.deleteFile(idFile);
    }

    @Override
    public void updatePerformanceUser(Task taskObj, Release releaseObj, Project projectObj) {
        Users userObj = userService.getUserById(taskObj.getAssignedTo().getId());
        Float performanceUser = Float.valueOf(0);
        Float weightUser = Float.valueOf(0);
        List<Task> taskListUser = new ArrayList<>();
        taskListUser = taskRepository.findTaskByAssignedToId(userObj.getId());
        for (Task task : taskListUser) {
            performanceUser = performanceUser + (task.getWeight() * task.getTaskProsentase() * releaseObj.getWeight() * projectObj.getWeight());
            weightUser = weightUser + (task.getWeight() * task.getRelease().getWeight() * task.getRelease().getProject().getWeight());
        }
        userObj.setTotalWeight(weightUser);
        userObj.setTotalPerformance(performanceUser);
        userService.saveUser(userObj);
    }

    @Override
    public void updateProsentaseProject(Project projectObj) {
        List<Release> releaseList = new ArrayList<>();
        Float prosentaseProject = Float.valueOf(0);
        releaseList = releaseService.getReleaseByProjectId(projectObj.getId());
        for (Release release : releaseList) {
            prosentaseProject = prosentaseProject + (release.getWeight() * release.getProsentaseRelease());
        }
        projectObj.setProsentaseProject(prosentaseProject);
        projectService.saveProject(projectObj);
    }

    @Override
    public void updateProsentaseRelease(Release releaseObj) {
        List<Task> taskList = new ArrayList<>();
        Float prosentaseRelease = Float.valueOf(0);
        taskList = taskRepository.findTaskByReleaseId(releaseObj.getId());
        for (Task task : taskList) {
            prosentaseRelease = prosentaseRelease + (task.getWeight() * task.getTaskProsentase());
        }
        releaseObj.setProsentaseRelease(prosentaseRelease);
        releaseService.saveRelease(releaseObj);
    }
}
