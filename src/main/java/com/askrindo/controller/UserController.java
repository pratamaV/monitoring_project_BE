package com.askrindo.controller;


import com.askrindo.entity.Users;
import com.askrindo.security.UsersServiceAuth;
import com.askrindo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UsersServiceAuth usersServiceAuth;

    @PostMapping("/user")
    public void saveUser(@RequestBody Users users){
        userService.saveUser(users);
    }

    @GetMapping("/user/{id}")
    public Users getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public List<Users> getAllUser(){
        return userService.getAllUser();
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody Users users){
        userService.updateUser(users);
    }

    @PutMapping("/user/{id}")
    public void updateStatusUserById(@PathVariable String id){
        userService.updateStatusUserById(id);
    }

    @GetMapping("/usersByStatus")
    public List<Users> getUserByStatusUser(@RequestParam String status){
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

    @PostMapping("/signup")
    public void signup(@RequestBody Users users) throws IOException {
        usersServiceAuth.registerUser(users);
    }

    @PutMapping(value = "/userForgotPassword")
    public void changePassword(@RequestParam(value = "email", required = true) String email) {
        usersServiceAuth.updatePassword(email);
    }

    @GetMapping("/userByPerformance")
    public List<Users> getUserByTotalPerformance(){
        return userService.getUsersByTotalPerformanceAsc();
    }
}
