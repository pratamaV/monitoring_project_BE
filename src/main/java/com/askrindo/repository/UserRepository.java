package com.askrindo.repository;

import com.askrindo.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    public List<Users> findByOrderByTotalPerformanceDesc();

    @Query(nativeQuery = true, value = "SELECT * FROM mst_user WHERE username ilike %:username% ORDER BY username ASC")
    Page<Users> getAllUserSearch(@Param("username") String username,
                                       Pageable pageable);
}
