package com.askrindo.repository;

import com.askrindo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by DELL on 26/02/2021.
 */

@Repository
public interface UserRepository extends JpaRepository<Users, String>, JpaSpecificationExecutor<Users> {
    public List<Users> findUserByStatusUser(String status);
    Optional<Users> findUsersByEmail(String email);
}
