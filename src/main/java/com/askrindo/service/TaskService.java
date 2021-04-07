package com.askrindo.service;

import com.askrindo.entity.File;
import com.askrindo.entity.Project;
import com.askrindo.entity.Release;
import com.askrindo.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */
public interface TaskService {
    public Task saveTaskOrdinary(Task task);
    public void saveTask(Task task);
    public void addTask(Task task);
    public void uploadDocumentById(MultipartFile taskDoc,File file, String taskId);
    public List<Task> getAllTask();
    public Task getTaskById(String id);
    public void updateTask(Task task);
    public void deleteAllTask();
    public List<Task> getTaskByReleaseId(String id);
    public void updateTaskByReleaseId(Task task,String id);
    public Float getWeightTaskByUserId(String userID);
    public Page<Task> getTaskByUserId(String id, String statusDone, String releaseName,
                                      String projectName,
                                      Date estStartDateFrom,
                                      Date estStartDateTo,
                                      Date estEndDateFrom,
                                      Date estEndDateTo, Pageable pageable);
;
    public void updatePerformanceUser(Task taskObj, Release releaseObj, Project projectObj);
    public void updateProsentaseProject(Project projectObj);
    public void updateProsentaseRelease(Release releaseObj);
    public Page<Task> getTaskByReleaseId(String id, String userId, String statusDone, Pageable pageable);
    public List<Task> getTaskAfterDeadline();
    public void updateDoneTask(String idTask, Float prosentase);
    public void deleteTaskFile(String idFile);
    public Page<Task> getTaskByReleaseIdWithSort(String idRelease, String orderBy, String sort, Integer page, Integer sizePerpage);
    public Page<Task> getTaskByUserIdWithSort(String idUser, String orderBy, String sort, Integer page, Integer sizePerpage);
        //    public String generateTaskCode(String idRelease);
}
