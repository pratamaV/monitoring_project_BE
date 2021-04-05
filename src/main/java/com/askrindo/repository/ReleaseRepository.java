package com.askrindo.repository;

import com.askrindo.entity.Release;
import io.swagger.models.auth.In;
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
    List<Release> findAllByProjectId(String idProject, Sort sort);

    public Integer countReleaseByProjectId(String IdProject);
    @Query(nativeQuery = true, value = "SELECT mst_release.* FROM mst_release inner join mst_project on mst_release.project_id  = mst_project.id where mst_project.id like %:projectId% and mst_release.status like %:status% and mst_release.stage like %:stage% ORDER BY release_name ASC")
    public List<Release> getReleasebyId2(@Param("projectId") String projectId,@Param("status") String status,@Param("stage") String stage);

    @Query(nativeQuery = true, value = "SELECT mst_release.* FROM mst_release inner join mst_project on mst_release.project_id  = mst_project.id where mst_project.id = :projectId ORDER BY :param ASC")
    public List<Release> getReleasebyIdWithSortASC(@Param("projectId") String projectId, @Param("param") String param);

    @Query(nativeQuery = true, value = "SELECT mst_release.* FROM mst_release inner join mst_project on mst_release.project_id  = mst_project.id where mst_project.id = :projectId ORDER BY :param DESC")
    public List<Release> getReleasebyIdWithSortDESC(@Param("projectId") String projectId, @Param("param") String param);

}
