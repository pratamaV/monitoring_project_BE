package com.askrindo.service;

import com.askrindo.GlobalKey;
import com.askrindo.entity.Division;
import com.askrindo.entity.Users;
import com.askrindo.pojo.UserPojo;
import com.askrindo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    DivisionService divisionService;

    @Override
    public void saveUser(Users users) {
        userRepository.save(users);
    }

    @Override
    public Page<Users> getAllUser(String username, Integer page, Integer sizePerPage, String orderBy , String sort) {
        if (sort.equals(GlobalKey.SORT_ASC)) {
            Pageable paging = PageRequest.of(page, sizePerPage, Sort.by(Sort.Direction.ASC, orderBy));
            return userRepository.getAllUserSearch(username, paging);
        } else if (sort.equals(GlobalKey.SORT_DESC)) {
            Pageable paging = PageRequest.of(page, sizePerPage, Sort.by(Sort.Direction.DESC, orderBy));
            return userRepository.getAllUserSearch(username, paging);
        }
        return null;
    }

    @Override
    public List<Users> getAllUserList() {
        return userRepository.findAll();
    }


    @Override
    public Users getUserById(String id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void updateUser(String id, UserPojo userPojo) {
        Users user = userRepository.findById(id).get();
        Division division = divisionService.getDivisionById(userPojo.getDivisiUser().getId());
        user.setUsername(userPojo.getUsername());
        user.setUserRole(userPojo.getUserRole());
        user.setDivisiUser(division);
        user.setEmail(userPojo.getEmail());
        user.setDirectorateUser(userPojo.getDirectorateUser());
        userRepository.save(user);
    }

    @Override
    public void updateStatusUserById(String id, String status) {
        Users userObj = userRepository.findById(id).get();
//        if (users.getStatusUser().equalsIgnoreCase("Active")){
        userObj.setStatusUser(status);
        userRepository.save(userObj);
//        } else {
//            users.setStatusUser("Active");
//            userRepository.save(users);
//        }
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
