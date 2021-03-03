package com.askrindo.service;

import com.askrindo.entity.User;
import com.askrindo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateStatusUserById(String id) {
        User user = userRepository.findById(id).get();
        if (user.getStatusUser().equalsIgnoreCase("aktif")){
            user.setStatusUser("tidak aktif");
            userRepository.save(user);
        } else {
            user.setStatusUser("aktif");
            userRepository.save(user);
        }
    }

    @Override
    public List<User> getUserByStatus(String status) {
        return userRepository.findUserByStatusUser(status);
    }

    @Override
    public Float getTotalWeightById(String id) {
        User user = userRepository.findById(id).get();
        return user.getTotalWeight();
    }

    @Override
    public Float getTotalPerformanceById(String id) {
        User user = userRepository.findById(id).get();
        return user.getTotalPerformance();
    }
}
