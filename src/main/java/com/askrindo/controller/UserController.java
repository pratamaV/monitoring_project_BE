package com.askrindo.controller;


import com.askrindo.entity.Division;
import com.askrindo.entity.Project;
import com.askrindo.entity.Users;
import com.askrindo.pojo.UserPojo;
import com.askrindo.security.UsersServiceAuth;
import com.askrindo.service.DivisionService;
import com.askrindo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @PostMapping("/users")
    public void saveUserArray(@RequestBody List<Users> users) throws IOException {
        for (Users users1:users) {
            usersServiceAuth.registerUser(users1);
        }
    }

    @PostMapping("/user/change-all-password")
    public void changePasswordAllUser(@RequestParam(name = "newPassword") String newPassword) throws IOException {
        usersServiceAuth.changeAllPassword(newPassword);

    }


    @GetMapping("/user/{id}")
    public Users getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public Page<Users> getAllUser(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                  @RequestParam(name = "size", defaultValue = "10") Integer sizePerPage,
                                  @RequestParam(name = "username", required = false) String username,
                                  @RequestParam(name = "orderBy", required = false) String orderBy,
                                  @RequestParam(name = "sort", required = false) String sort
                                  ){
//        Pageable pageable = PageRequest.of(page, sizePerPage);
        return userService.getAllUser(username, page, sizePerPage, orderBy, sort);
    }

    @GetMapping("/users-list")
    public List<Users> getAllUserList(){
        return userService.getAllUserList();
    }

//    @PutMapping("/user")
//    public void updateUser(@RequestBody Users users){
//        userService.updateUser(users);
//    }

    @PutMapping("/user-update/{id}")
    public void updateUser(@PathVariable String id,
                           @RequestBody UserPojo userPojo){
        userService.updateUser(id, userPojo);
    }

    @PutMapping("/user/{id}")
    public void updateStatusUserById(@PathVariable String id,
                                     @RequestParam(value = "status", required = true) String status){
        userService.updateStatusUserById(id, status);
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
    public void forgotPassword(@RequestParam(value = "email", required = true) String email) {
        usersServiceAuth.forgotPassword(email);
    }

    @PutMapping(value = "/userChangePassword/{id}")
    public void changePassword(@PathVariable(name = "id") String id,
                               @RequestParam(value = "oldPassword", required = true) String oldPassword,
                               @RequestParam(value = "newPassword", required = true) String newPassword) {
        usersServiceAuth.updatePassword(id, oldPassword ,newPassword);
    }

    @GetMapping("/userByPerformance")
    public List<Users> getUserByTotalPerformance(){
        return userService.getUsersByTotalPerformanceAsc();
    }
}
