package com.askrindo.service;

import com.askrindo.entity.Users;
import com.askrindo.pojo.UserPojo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */
public interface UserService {
    public void saveUser(Users users);
    public Page<Users> getAllUser(String username, Integer page, Integer sizePerPage, String orderBy , String sort);
    public List<Users> getAllUserList();
    public Users getUserById(String id);
    public void updateUser(String id, UserPojo user);
    public void updateStatusUserById(String id, String status);
    public List<Users> getUserByStatus(String status);
    public Float getTotalWeightById(String id);
    public Float getTotalPerformanceById(String id);
    public List<Users> getUsersByTotalPerformanceAsc();
}
