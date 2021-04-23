package com.askrindo.repository;

import com.askrindo.entity.Project;
import com.askrindo.entity.Release;
import io.swagger.models.auth.In;
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
public interface ReleaseRepository extends JpaRepository<Release, String>, JpaSpecificationExecutor<Release> {
    public List<Release> findReleaseByProjectId(String id);
    public List<Release> findReleaseByStage(String stage);
    public List<Release> findReleaseByStatus(String status);
    public List<Release> findReleaseByStatusReleaseAndProjectId(String statusRelease, String id);
    List<Release> findAllByProjectId(String idProject, Pageable pageable);

    public Integer countReleaseByProjectId(String IdProject);
//    @Query(nativeQuery = true, value = "SELECT mst_release.* FROM mst_release inner join mst_project on mst_release.project_id  = mst_project.id where mst_project.id like %:projectId% and mst_release.status like %:status% and mst_release.stage like %:stage% ORDER BY release_name ASC")
//    public List<Release> getReleasebyId2(@Param("projectId") String projectId,@Param("status") String status,@Param("stage") String stage);

    @Query(nativeQuery = true, value = "select mst_release.* from mst_release inner join mst_division on mst_release.division_id = mst_division.id inner join mst_project on mst_release.project_id  = mst_project.id inner join mst_user on mst_release.pm_id = mst_user.id  where mst_project.id like %:projectId% and mst_release.pm_id like %:pmId%  and mst_release.pmo_id like %:pmoId%  and mst_release.copm_id like %:copmId%  and mst_release.status like %:status%  and mst_release.stage like %:stage% and mst_release.division_id like %:divisionId%  and mst_release.directorate_user like %:directoratUser% and mst_project.project_code like %:projectCode% and mst_project.project_name like %:projectName% and mst_release.development_mode like %:developmentMode% order by mst_release.release_code asc")
    Page<Release> getAllReleaseByIdProject(
                                @Param("projectId") String projectId,
                                @Param("pmId") String pmId,
                                @Param("pmoId") String pmoId,
                                @Param("copmId") String copmId,
                                @Param("status") String status,
                                @Param("stage") String stage,
                                @Param("divisionId") String divisionId,
                                @Param("directoratUser") String directoratUser,
                                @Param("projectCode") String projectCode,
                                @Param("projectName") String projectName,
                                @Param("developmentMode") String developmentMode, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT mst_release.* FROM mst_release inner join mst_project on mst_release.project_id  = mst_project.id where mst_project.id like %:projectId% and mst_release.status like %:status% and mst_release.stage like %:stage% ORDER BY release_name ASC")
    public Page<Release> getReleasebyId2(@Param("projectId") String projectId, @Param("status") String status, @Param("stage") String stage, Pageable pageable);


    @Query(nativeQuery = true, value = "SELECT mst_release.* FROM mst_release inner join mst_project on mst_release.project_id  = mst_project.id where mst_project.id = :projectId ORDER BY :param ASC")
    public List<Release> getReleasebyIdWithSortASC(@Param("projectId") String projectId, @Param("param") String param);

    @Query(nativeQuery = true, value = "SELECT mst_release.* FROM mst_release inner join mst_project on mst_release.project_id  = mst_project.id where mst_project.id = :projectId ORDER BY :param DESC")
    public List<Release> getReleasebyIdWithSortDESC(@Param("projectId") String projectId, @Param("param") String param);

    public List<Release> findAllByCoPMId(String id);

    @Query(nativeQuery = true, value = "select mst_release.* from mst_release inner join mst_division on mst_release.division_id = mst_division.id inner join mst_project on mst_release.project_id  = mst_project.id inner join mst_user on mst_release.pm_id = mst_user.id where mst_project.project_name like %:projectName% and mst_release.pm_id like %:pmId%  and mst_release.pmo_id like %:pmoId% and mst_release.copm_id like %:copmId%  and mst_release.status like %:status%  and mst_release.stage like %:stage% and mst_release.division_id like %:divisionId%  and mst_release.directorate_user like %:directoratUser% and mst_release.development_mode like %:developmentMode% order by mst_release.release_code asc")
    Page<Release> getAllReleaseByFilter(
            @Param("projectName") String projectName,
            @Param("pmId") String pmId,
            @Param("pmoId") String pmoId,
            @Param("copmId") String copmId,
            @Param("status") String status,
            @Param("stage") String stage,
            @Param("divisionId") String divisionId,
            @Param("directoratUser") String directoratUser,
            @Param("developmentMode") String developmentMode,
            Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM mst_release INNER JOIN mst_project ON mst_release.project_id = mst_project.id WHERE mst_project.project_dependency ilike %:projectDependency% OR mst_project.project_name ilike %:projectName% order by mst_release.release_code asc")
    Page<Release> getAllReleaseSearch(@Param("projectDependency") String projectDependency,
                                      @Param("projectName") String projectName,
                                      Pageable pageable);




}
