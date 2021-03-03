package com.askrindo.repository;

import com.askrindo.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    public List<Project> findProjectByStatusProject(String statusProject);
}
