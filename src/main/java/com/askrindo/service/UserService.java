package com.askrindo.service;

import com.askrindo.entity.Users;

import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */
public interface UserService {
    public void saveUser(Users users);
    public List<Users> getAllUser();
    public Users getUserById(String id);
    public void updateUser(Users users);
    public void updateStatusUserById(String id, String status);
    public List<Users> getUserByStatus(String status);
    public Float getTotalWeightById(String id);
    public Float getTotalPerformanceById(String id);
    public List<Users> getUsersByTotalPerformanceAsc();
}
