package com.askrindo.repository;

import com.askrindo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    public List<User> findUserByStatusUser(String status);
}
