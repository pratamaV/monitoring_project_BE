package com.askrindo.controller;

import com.askrindo.entity.Project;
import com.askrindo.entity.Release;
import com.askrindo.entity.Task;
import com.askrindo.entity.User;
import com.askrindo.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@RestController
public class TaskController {
    @Autowired
    TaskService taskService;

    @Value("${document-task}")
    String documentTask;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/task")
    public void saveTask(@RequestPart(required = false) MultipartFile taskDoc,
                         @RequestParam String taskName,
                         @RequestParam String taskCode,
                         @RequestParam String assignedTo,
                         @RequestParam Integer score,
                         @RequestParam Float weight,
                         @RequestParam String statusDone,
                         @RequestParam Float taskProsentase,
                         @RequestParam Date finalTarget,
                         @RequestParam String release
                         ) throws JsonProcessingException {
        try{
            if (taskDoc != null) {
                taskDoc.transferTo(Paths.get(documentTask, "TD-" + taskName + "." + FilenameUtils.getExtension(taskDoc.getOriginalFilename())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(taskDoc);
        System.out.println(assignedTo);

        String taskDocument = StringUtils.cleanPath("TD-" + taskName + "." + FilenameUtils.getExtension(taskDoc.getOriginalFilename()));
        User assignedTo1 = objectMapper.readValue(assignedTo, User.class);
        Release release1 = objectMapper.readValue(release, Release.class);
        Task newTask = new Task(taskName, taskCode, assignedTo1, score, weight, statusDone, taskProsentase, finalTarget, taskDocument, release1);
        taskService.saveTask(newTask);
    }

    @PostMapping("/addTask")
    public void addTask(@RequestBody Task task){
        taskService.addTask(task);
    }

    @PutMapping("/uploadTaskDoc/{id}")
    public void uploadTaskDocument(@RequestPart(required = false) MultipartFile taskDoc,
                         @PathVariable String id) throws JsonProcessingException {
        Task task = taskService.getTaskById(id);
        String taskName = task.getTaskName();
        try{
            if (taskDoc != null) {
                taskDoc.transferTo(Paths.get(documentTask, "TD-" + taskName + "." + FilenameUtils.getExtension(taskDoc.getOriginalFilename())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String taskDocument = StringUtils.cleanPath("TD-" + taskName + "." + FilenameUtils.getExtension(taskDoc.getOriginalFilename()));
        taskService.uploadDocumentById(taskDocument, id);
    }

    @GetMapping("/task/{id}")
    public Task getTaskById(@PathVariable String id){
        return taskService.getTaskById(id);
    }

    @GetMapping("/tasks")
    public List<Task> getAllTask(){
        return taskService.getAllTask();
    }

    @PutMapping("/task")
    public void updateTask(@RequestBody Task task){
        taskService.updateTask(task);
    }

    @PutMapping("/task/{idRelease}")
    public void updateTask(@PathVariable String idRelease,
                         @RequestParam String id,
                         @RequestParam String taskName,
                           @RequestParam String taskCode,
                         @RequestParam String assignedTo,
                         @RequestParam Integer score,
                         @RequestParam Float weight,
                         @RequestParam String statusDone,
                         @RequestParam Float taskProsentase,
                         @RequestParam Date finalTarget,
                         @RequestParam String release
    ) throws JsonProcessingException {
        User assignedTo1 = objectMapper.readValue(assignedTo, User.class);
        Release release1 = objectMapper.readValue(release, Release.class);
        Task newTask = new Task(id, taskName, taskCode, assignedTo1, score, weight, statusDone, taskProsentase, finalTarget, release1);
        taskService.updateTaskByReleaseId(newTask, idRelease);
    }

    @DeleteMapping("/tasks")
    public void deleteAllTask(){
        taskService.deleteAllTask();
    }

    @GetMapping("/weightTaskByUserId/{id}")
    public Float getWeightTaskByUserId(@PathVariable String id){
        return taskService.getWeightTaskByUserId(id);
    }

    @GetMapping("/taskByReleaseId/{id}")
    public List<Task> getTaskByReleaseId(@PathVariable String id){
        return taskService.getTaskByReleaseId(id);
    }
}
