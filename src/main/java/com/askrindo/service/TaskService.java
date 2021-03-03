package com.askrindo.service;

import com.askrindo.entity.Task;

import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */
public interface TaskService {
    public void saveTask(Task task);
    public List<Task> getAllTask();
    public Task getTaskById(String id);
    public void updateTask(Task task);
    public void deleteAllTask();
    public List<Task> getTaskByReleaseId(String id);
    public void updateTaskByReleaseId(Task task,String id);
    public Float getWeightTaskByUserId(String userID);
}
