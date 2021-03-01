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
}
