package com.askrindo.repository;

import com.askrindo.entity.*;
import com.sun.org.apache.xpath.internal.operations.Div;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
//    public List<Project> getAllProjectforUser();
    public List<Project> findProjectByDivisiUserAndStatusProject(String divisiUser, String statusProject);

//    Project findProjectAA(Division division, Users pm, Users pmo, String directorateUser);

//    @Query("SELECT p FROM Project p WHERE p.divisiUser = ?1 OR p.categoryActivity = ?1 OR p.statusProject = ?1")
//    Project findProjectAllQuery(Division division, String categoryActivity, String statusProject);

    @Query(nativeQuery = true, value = "SELECT * FROM mst_project as p, mst_division as d, mst_user as u " +
            "WHERE p.division_id = d.id " +
            "AND p.pm_id = u.id " +
            "AND p.division_id = :division " +
            "AND p.pm_id = :pm " +
            "AND p.pmo_id = :pmo " +
            "AND p.directorate_user = :directorateUser")
    Project findProjectAA(@Param("division") Division division,@Param("pm") Users pm,@Param("pmo") Users pmo,@Param("directorateUser") String directorateUser);

    @Query(nativeQuery = true, value = "SELECT * FROM mst_project as p, mst_user as u " +
            "WHERE p.pm_id = u.id " +
            "AND p.pm_id = :pm " +
            "AND p.pmo_id = :pmo " +
            "AND p.directorate_user = :directorateUser")
    Project findProjectAB(@Param("pm") Users pm,@Param("pmo") Users pmo,@Param("directorateUser") String directorateUser);

    @Query(nativeQuery = true, value = "SELECT * FROM mst_project as p, mst_user as u " +
            "WHERE p.pm_id = u.id " +
            "AND p.pm_id = :pm " +
            "AND p.pmo_id = :pmo")
    Project findProjectAC(@Param("pm") Users pm,@Param("pmo") Users pmo);

    @Query(nativeQuery = true, value = "SELECT * FROM mst_project as p, mst_user as u " +
            "WHERE p.pmo_id = u.id " +
            "AND p.pmo_id = :pmo")
    Project findProjectAD(@Param("pmo") Users pmo);


    @Query(nativeQuery = true, value = "SELECT * FROM mst_project as p, mst_user as u " +
            "WHERE p.pm_id = u.id " +
            "AND p.pm_id = :pm")
    Project findProjectAE(@Param("pm") Users pm);

    @Query(nativeQuery = true, value = "SELECT * FROM mst_project as p, mst_division as d, mst_user as u " +
            "WHERE p.division_id = d.id " +
            "AND p.pm_id = u.id " +
            "AND p.division_id = :division " +
            "AND p.pm_id = :pm " +
            "AND p.directorate_user = :directorateUser")
    Project findProjectAF(Division division, Users pm, String directorateUser);
}
