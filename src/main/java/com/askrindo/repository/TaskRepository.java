package com.askrindo.repository;

import com.askrindo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    public List<Task> findTaskByReleaseId(String id);
}
