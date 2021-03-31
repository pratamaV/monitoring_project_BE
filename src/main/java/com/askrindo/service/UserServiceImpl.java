package com.askrindo.service;

import com.askrindo.entity.Users;
import com.askrindo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public void saveUser(Users users) {
        userRepository.save(users);
    }

    @Override
    public List<Users> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Users getUserById(String id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void updateUser(Users users) {
        userRepository.save(users);
    }

    @Override
    public void updateStatusUserById(String id) {
        Users users = userRepository.findById(id).get();
        if (users.getStatusUser().equalsIgnoreCase("Active")){
            users.setStatusUser("Not Active");
            userRepository.save(users);
        } else {
            users.setStatusUser("Active");
            userRepository.save(users);
        }
    }

    @Override
    public List<Users> getUserByStatus(String status) {
        return userRepository.findUserByStatusUser(status);
    }

    @Override
    public Float getTotalWeightById(String id) {
        Users users = userRepository.findById(id).get();
        return users.getTotalWeight();
    }

    @Override
    public Float getTotalPerformanceById(String id) {
        Users users = userRepository.findById(id).get();
        return users.getTotalPerformance();
    }

    @Override
    public List<Users> getUsersByTotalPerformanceAsc() {
        return userRepository.findByOrderByTotalPerformanceDesc();
    }
}
