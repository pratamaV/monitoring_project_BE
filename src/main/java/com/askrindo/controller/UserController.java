package com.askrindo.controller;


import com.askrindo.entity.Project;
import com.askrindo.entity.User;
import com.askrindo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public void saveUser(@RequestBody User user){
        userService.saveUser(user);
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody User user){
        userService.updateUser(user);
    }

    @PutMapping("/user/{id}")
    public void updateStatusUserById(@PathVariable String id){
        userService.updateStatusUserById(id);
    }

    @GetMapping("/usersByStatus")
    public List<User> getUserByStatusUser(@RequestParam String status){
        return userService.getUserByStatus(status);
    }

    @GetMapping("/userWeight/{id}")
    public Float getTotalWeightById(@PathVariable String id){
        return userService.getTotalWeightById(id);
    }

    @GetMapping("/userPerformance/{id}")
    public Float getTotalPerformanceById(@PathVariable String id){
        return userService.getTotalPerformanceById(id);
    }
}
