package com.askrindo.service;

import com.askrindo.entity.File;
import com.askrindo.entity.Task;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */
public interface TaskService {
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
    public List<Task> getTaskByUserId(String id, String statusDone, String releaseName,
                                      String projectName,
                                      Date estStartDateFrom,
                                      Date estStartDateTo,
                                      Date estEndDateFrom,
                                      Date estEndDateTo);
;
    public List<Task> getTaskByReleaseId(String id, String userId, String statusDone);
    public List<Task> getTaskAfterDeadline();
    public void updateDoneTask(String idTask, Float prosentase);
    public void deleteTaskFile(String idFile);
//    public String generateTaskCode(String idRelease);
}
