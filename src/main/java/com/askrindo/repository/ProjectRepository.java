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
//    public List<Project> getAllProjectforUser();
    public List<Project> findProjectByDivisiUserAndStatusProject(String divisiUser, String statusProject);
    public List<Project> findProjectByLineItem(String lineItem);
//    public Page<Project> findAll(Pageable pageable, Sort sort);

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
    List<Project> findProjectAA(@Param("division") Division division,@Param("pm") Users pm,@Param("pmo") Users pmo,@Param("directorateUser") String directorateUser);

    @Query(nativeQuery = true, value = "SELECT * FROM mst_project as p, mst_user as u " +
            "WHERE p.pm_id = u.id " +
            "AND p.pm_id = :pm " +
            "AND p.pmo_id = :pmo " +
            "AND p.directorate_user = :directorateUser")
    List<Project> findProjectAB(@Param("pm") Users pm,@Param("pmo") Users pmo,@Param("directorateUser") String directorateUser);

    @Query(nativeQuery = true, value = "SELECT * FROM mst_project as p, mst_user as u " +
            "WHERE p.pm_id = u.id " +
            "AND p.pm_id = :pm " +
            "AND p.pmo_id = :pmo")
    List<Project> findProjectAC(@Param("pm") Users pm,@Param("pmo") Users pmo);

    @Query(nativeQuery = true, value = "SELECT * FROM mst_project as p, mst_user as u " +
            "WHERE p.pmo_id = u.id " +
            "AND p.pmo_id = :pmo")
    List<Project> findProjectAD(@Param("pmo") Users pmo);


    @Query(nativeQuery = true, value = "SELECT * FROM mst_project as p, mst_user as u " +
            "WHERE p.pm_id = u.id " +
            "AND p.pm_id = :pm")
    List<Project> findProjectAE(@Param("pm") Users pm);

    @Query(nativeQuery = true, value = "SELECT * FROM mst_project as p, mst_division as d, mst_user as u " +
            "WHERE p.division_id = d.id " +
            "AND p.pm_id = u.id " +
            "AND p.division_id = :division " +
            "AND p.pm_id = :pm " +
            "AND p.directorate_user = :directorateUser")
    List<Project> findProjectAF(@Param("division") Division division,@Param("pm") Users pm, @Param("directorateUser") String directorateUser);

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM mst_project as p WHERE p.division_id = :division")
    List<Project> findProjectAG(@Param("division") Division division);

    @Query(nativeQuery = true, value = "SELECT * FROM mst_project as p, mst_division as d, mst_user as u " +
            "WHERE p.division_id = d.id " +
            "AND p.pm_id = u.id " +
            "AND p.division_id = :division " +
            "AND p.directorate_user = :directorateUser")
    List<Project> findProjectAH(@Param("division") Division division, @Param("directorateUser") String directorateUser);

    @Query(nativeQuery = true, value = "SELECT * FROM mst_project as p, mst_division as d, mst_user as u " +
            "WHERE p.division_id = d.id " +
            "AND p.pmo_id = u.id " +
            "AND p.division_id = :division " +
            "AND p.pmo_id = :pmo " +
            "AND p.directorate_user = :directorateUser")
    List<Project> findProjectAI(@Param("division") Division division, @Param("pmo") Users pmo,@Param("directorateUser") String directorateUser);


    @Query(nativeQuery = true, value = "SELECT * FROM mst_project as p, mst_division as d, mst_user as u " +
            "WHERE p.division_id = d.id " +
            "AND p.pm_id = u.id " +
            "AND p.division_id = :division " +
            "AND p.pm_id = :pm ")
    List<Project> findProjectAJ(@Param("division") Division division, @Param("pm") Users pm);

    @Query(nativeQuery = true, value = "SELECT * FROM mst_project as p, mst_division as d, mst_user as u " +
            "WHERE p.division_id = d.id " +
            "AND p.pm_id = u.id " +
            "AND p.pmo_id = :pmo " +
            "AND p.directorate_user = :directorateUser")
    List<Project> findProjectAK(@Param("pmo") Users pmo,@Param("directorateUser") String directorateUser);

    @Query(nativeQuery = true, value = "SELECT * FROM mst_project as p, mst_division as d, mst_user as u " +
            "WHERE p.division_id = d.id " +
            "AND p.pm_id = u.id " +
            "AND p.pm_id = :pm " +
            "AND p.directorate_user = :directorateUser")
    List<Project> findProjectAL(@Param("pm") Users pm,@Param("directorateUser") String directorateUser);

    @Query(nativeQuery = true, value = "SELECT * FROM mst_project as p WHERE p.directorate_user = :directorateUser")
    List<Project> findProjectAM(@Param("directorateUser") String directorateUser);

    List<Project> findProjectByPmId(String id);
    List<Project> findProjectByPmoId(String id);
    List<Project> findProjectByCoPMId(String id);

    @Query(nativeQuery = true, value = "SELECT * FROM mst_project WHERE keyword ilike %:keyword%")
    List<Project> findProjectByKeyword(@Param("keyword") String keyword);

//    @Query(nativeQuery = true, value = "select mst_project.* from mst_project inner join mst_division on mst_project.division_id = mst_division.id inner join mst_user on mst_project.pm_id = mst_user.id where mst_project.division_id like %:divisionId% and mst_project.pm_id like %:pmId% and mst_project.pmo_id like %:pmoId% and mst_project.status like %:statusProject% and mst_project.directorate_user like %:directoratUser% " +
//            "order by mst_project.project_name ASC")
//    List<Project> getAllProject(@Param("divisionId") String divisionId,
//                                @Param("pmId") String pmId,
//                                @Param("pmoId") String pmoId,
//                                @Param("statusProject") String statusProject,
//                                @Param("directoratUser") String directoratUser);

    @Query(nativeQuery = true, value = "select mst_project.* from mst_project inner join mst_division on mst_project.division_id = mst_division.id inner join mst_user on mst_project.pm_id = mst_user.id where mst_project.division_id like %:divisionId% and mst_project.pm_id like %:pmId% and mst_project.pmo_id like %:pmoId% and mst_project.status like %:statusProject% and mst_project.directorate_user like %:directoratUser% " +
            "order by mst_project.project_name ASC")
    Page<Project> getAllProject(@Param("divisionId") String divisionId,
                                @Param("pmId") String pmId,
                                @Param("pmoId") String pmoId,
                                @Param("statusProject") String statusProject,
                                @Param("directoratUser") String directoratUser, Pageable pageable);
}
