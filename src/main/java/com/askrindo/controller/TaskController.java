package com.askrindo.controller;

import com.askrindo.entity.File;
import com.askrindo.entity.Release;
import com.askrindo.entity.Task;
import com.askrindo.entity.Users;
import com.askrindo.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
//                         @RequestParam String taskCode,
                         @RequestParam String assignedTo,
                         @RequestParam Integer score,
                         @RequestParam Float weight,
                         @RequestParam String statusDone,
                         @RequestParam Float taskProsentase,
                         @RequestParam Date finalTarget,
                         @RequestParam String release
    ) throws JsonProcessingException {
        try {
            if (taskDoc != null) {
                taskDoc.transferTo(Paths.get(documentTask, "TD-" + taskName + "." + FilenameUtils.getExtension(taskDoc.getOriginalFilename())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(taskDoc);
        System.out.println(assignedTo);
        String taskCode = null;
        String taskDocument = StringUtils.cleanPath("TD-" + taskName + "." + FilenameUtils.getExtension(taskDoc.getOriginalFilename()));
        Users assignedTo1 = objectMapper.readValue(assignedTo, Users.class);
        Release release1 = objectMapper.readValue(release, Release.class);
        Task newTask = new Task(taskName, taskCode, assignedTo1, score, weight, statusDone, taskProsentase, finalTarget, taskDocument, release1);
        taskService.saveTask(newTask);
    }


    @PostMapping("/tasks")
    public void saveTask(@RequestBody Task [] tasks){
        for (Task task: tasks) {
            taskService.addTask(task);
        }

    }

    @PostMapping("/addTask")
    public void addTask(@RequestBody Task task) {
        taskService.addTask(task);
    }

//    @PutMapping("/uploadTaskDoc/{id}")
//    public void uploadTaskDocument(@RequestPart(required = false) MultipartFile taskDoc,
//                                   @PathVariable String id) throws JsonProcessingException {
//        Task task = taskService.getTaskById(id);
//        String taskName = task.getTaskName();
//        try {
//            if (taskDoc != null) {
//                taskDoc.transferTo(Paths.get(documentTask, "TD-" + taskName + "." + FilenameUtils.getExtension(taskDoc.getOriginalFilename())));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String taskDocument = StringUtils.cleanPath("TD-" + taskName + "." + FilenameUtils.getExtension(taskDoc.getOriginalFilename()));
//        taskService.uploadDocumentById(taskDocument, id);
//    }

    @PostMapping("/uploadTaskDoc/{id}")
    public void uploadTaskDocument(@RequestPart(required = false) MultipartFile taskDoc,
                                   @PathVariable String id,
                                   @RequestParam(name = "data", required = false) String data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        File fileObj = mapper.readValue(data, File.class);
//        Task task = taskService.getTaskById(id);
//        String taskName = task.getTaskName();
//        try {
//            if (taskDoc != null) {
//                taskDoc.transferTo(Paths.get(documentTask, "TD-" + taskName + "." + FilenameUtils.getExtension(taskDoc.getOriginalFilename())));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String taskDocument = StringUtils.cleanPath("TD-" + taskName + "." + FilenameUtils.getExtension(taskDoc.getOriginalFilename()));
//        taskService.uploadDocumentById(taskDocument, id);
        taskService.uploadDocumentById(taskDoc, fileObj, id);
    }

    @GetMapping("/task/{id}")
    public Task getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/tasks")
    public List<Task> getAllTask() {
        return taskService.getAllTask();
    }

    @PutMapping("/task")
    public void updateTask(@RequestBody Task task) {
                taskService.updateTask(task);
    }

    @PutMapping("/task/{idRelease}")
    public void updateTask(
            @PathVariable String idRelease,
            @RequestParam String id,
            @RequestParam String taskName,
            @RequestParam String taskCode,
            @RequestParam String assignedTo,
            @RequestParam Integer score,
            @RequestParam Float weight,
            @RequestParam String statusDone,
            @RequestParam Float taskProsentase,
            @RequestParam Date estStartDate,
            @RequestParam Date estEndDate,
            @RequestParam Date actStartDate,
            @RequestParam Date actEndDate,
            @RequestParam String release
    ) throws JsonProcessingException {
//        try{
//            if (taskDoc != null) {
//                taskDoc.transferTo(Paths.get(documentTask, "TD-" + taskName + "." + FilenameUtils.getExtension(taskDoc.getOriginalFilename())));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        @RequestPart(required = false) MultipartFile taskDoc,
//        String taskDocument = StringUtils.cleanPath("TD-" + taskName + "." + FilenameUtils.getExtension(taskDoc.getOriginalFilename()));
        Users assignedTo1 = objectMapper.readValue(assignedTo, Users.class);
        Release release1 = objectMapper.readValue(release, Release.class);
        Task newTask = new Task(id, taskName, taskCode, assignedTo1, score, weight, statusDone, taskProsentase, estStartDate, actStartDate, estEndDate, actEndDate, release1);
        taskService.updateTaskByReleaseId(newTask, idRelease);
    }

    @PutMapping("/doneTask/{idRelease}")
    public void doneTask(@PathVariable String idRelease,
                         @RequestBody Task task) {
        taskService.updateTaskByReleaseId(task, idRelease);
    }

    @PutMapping("/doneTaskNew/{idTask}")
    public void updateDoneTask(@PathVariable String idTask,
                               @RequestParam(name = "prosentase", required = false) Float prosentase) {
        taskService.updateDoneTask(idTask,prosentase);
    }

    @DeleteMapping("/tasks")
    public void deleteAllTask() {
        taskService.deleteAllTask();
    }

    @GetMapping("/weightTaskByUserId/{id}")
    public Float getWeightTaskByUserId(@PathVariable String id) {
        return taskService.getWeightTaskByUserId(id);
    }

//    @GetMapping("/taskByReleaseId/{id}")
//    public List<Task> getTaskByReleaseId(@PathVariable String id){
//        return taskService.getTaskByReleaseId(id);
//    }

    @GetMapping("/taskByReleaseId/{id}")
    public Page<Task> getTaskByReleaseId(@PathVariable String id,
                                         @RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", defaultValue = "10") Integer sizePerPage,
                                         @RequestParam(name = "assignTo", required = false) String userId,
                                         @RequestParam(name = "statusDone", required = false) String statusDone) {
        if (userId == null && statusDone == null) {
            userId = "";
            statusDone = "";
        } else if (userId == null) {
            userId = "";
        } else if (statusDone == null) {
            statusDone = "";
        }
        Pageable pageable = PageRequest.of(page, sizePerPage);
        return taskService.getTaskByReleaseId(id, userId, statusDone, pageable);
    }

    @GetMapping("/taskByReleaseId-sort/{id}")
    public Page<Task> getTaskByReleaseIdWithSort(@PathVariable String id,
                                                 @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(name = "size", defaultValue = "10") Integer sizePerPage,
                                                 @RequestParam(name = "orderBy", required = false) String orderBy,
                                                 @RequestParam(name = "sort", required = false) String sort) {
        return taskService.getTaskByReleaseIdWithSort(id, orderBy, sort, page, sizePerPage);
    }

    @GetMapping("/taskByUserId-sort/{id}")
    public Page<Task> getTaskByUserIdWithSort(@PathVariable String id,
                                                 @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(name = "size", defaultValue = "10") Integer sizePerPage,
                                                 @RequestParam(name = "orderBy", required = false) String orderBy,
                                                 @RequestParam(name = "sort", required = false) String sort) {
        return taskService.getTaskByUserIdWithSort(id, orderBy, sort, page, sizePerPage);
    }


    @GetMapping("/taskByUserId/{id}")
    public Page<Task> getTaskByUserId(@PathVariable String id,
                                      @RequestParam(name = "page", defaultValue = "0") Integer page,
                                      @RequestParam(name = "size", defaultValue = "10") Integer sizePerPage,
                                      @RequestParam(name = "statusDone", required = false) String statusDone,
                                      @RequestParam(name = "releaseName", required = false) String releaseName,
                                      @RequestParam(name = "projectName", required = false) String projectName,
                                      @RequestParam(name = "estStartDateFrom", required = false) String estStartDateFrom,
                                      @RequestParam(name = "estEndDateFrom", required = false) String estEndDateFrom,
                                      @RequestParam(name = "estStartDateTo", required = false) String estStartDateTo,
                                      @RequestParam(name = "estEndDateTo", required = false) String estEndDateTo) throws ParseException {

        Date estStartDateFromParse = new Date();
        Date estStartDateToParse = new Date();
        Date estEndDateFromParse = new Date();
        Date estEndDateToParse = new Date();

        if (estStartDateFrom == ""){
            estStartDateFromParse = null;
        }else {
            estStartDateFromParse = new SimpleDateFormat("yyyy-MM-dd").parse(estStartDateFrom);
        }
        if (estStartDateTo == ""){
            estStartDateToParse = null;
        }else {
            estStartDateToParse = new SimpleDateFormat("yyyy-MM-dd").parse(estStartDateTo);
        }
        if (estEndDateFrom == ""){
            estEndDateFromParse = null;
        }else {
            estEndDateFromParse = new SimpleDateFormat("yyyy-MM-dd").parse(estEndDateFrom);
        }
        if (estEndDateTo== ""){
            estEndDateToParse = null;
        }else {
            estEndDateToParse = new SimpleDateFormat("yyyy-MM-dd").parse(estEndDateTo);
        }
        Pageable pageable = PageRequest.of(page, sizePerPage);
        return taskService.getTaskByUserId(id, statusDone, releaseName, projectName, estStartDateFromParse , estStartDateToParse , estEndDateFromParse , estEndDateToParse, pageable);
    }

    @GetMapping("/taskDeadline")
    public List<Task> getTaskDeadline() {
        return taskService.getTaskAfterDeadline();
    }

    @DeleteMapping("/task-file/{idFile}")
    public void deleteTaskFile(@PathVariable String idFile) {
        taskService.deleteTaskFile(idFile);
    }

//    @GetMapping("/taskCode")
//    public String TestGenerateReleaseCode() {
//
//        return taskService.generateTaskCode("2c951081786843430178685388f40000");
//    }

}
