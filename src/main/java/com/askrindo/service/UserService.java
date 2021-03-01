package com.askrindo.service;

import com.askrindo.entity.User;

import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */
public interface UserService {
    public void saveUser(User user);
    public List<User> getAllUser();
    public User getUserById(String id);
    public void updateUser(User user);
}
