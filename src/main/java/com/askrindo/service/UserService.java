package com.askrindo.service;

import com.askrindo.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */
public interface UserService {
    public void saveUser(Users users);
    public Page<Users> getAllUser(Pageable pageable);
    public List<Users> getAllUserList();
    public Users getUserById(String id);
    public void updateUser(Users users);
    public void updateStatusUserById(String id, String status);
    public List<Users> getUserByStatus(String status);
    public Float getTotalWeightById(String id);
    public Float getTotalPerformanceById(String id);
    public List<Users> getUsersByTotalPerformanceAsc();
}
