package com.askrindo.repository;

import com.askrindo.entity.Division;
import com.askrindo.entity.Project;
import com.askrindo.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, String>, JpaSpecificationExecutor<Project> {
    public List<Project> findProjectByStatusProject(String statusProject);
    @Query(nativeQuery = true, value = "SELECT * FROM mst_project WHERE project_dependency ilike %:projectDependency% OR project_name ilike %:projectName%")
    Page<Project> getAllProjectDependency(@Param("projectDependency") String projectDependency,
                                          @Param("projectName") String projectName,
                                          Pageable pageable);

    //    @Query(nativeQuery = true, value = "SELECT DISTINCT mst_project.* FROM mst_project INNER JOIN mst_release ON mst_project.id=mst_release.project_id where mst_release.coPM_id= :coPMId")
    //    public Page<Project> findProjectBycoPMId(@Param("coPMId") String coPMId, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT distinct mst_project.* FROM mst_project\n" +
            "    inner join mst_release\n" +
            "    on mst_project.id = mst_release.project_id\n" +
            "    WHERE mst_release.copm_id = :coPMId and (mst_project.project_dependency ilike %:projectDependency% OR mst_project.project_name ilike %:projectName%) ORDER BY mst_project.project_code ASC")
    public Page<Project> findProjectBycoPMId(@Param("coPMId") String coPMId, @Param("projectDependency") String projectDependency,
                                             @Param("projectName") String projectName,
                                             Pageable pageable);


    @Query(nativeQuery = true, value = "SELECT DISTINCT mst_project.* FROM mst_project INNER JOIN mst_release ON mst_project.id=mst_release.project_id where mst_release.directorate_user= :directorateUser AND mst_project.status_project='Active'")
    public List<Project> findProjectByDirectorateUser(@Param("directorateUser") String directorateUser);

}
