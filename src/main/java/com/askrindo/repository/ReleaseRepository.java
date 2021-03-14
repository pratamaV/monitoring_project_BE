package com.askrindo.repository;

import com.askrindo.entity.Release;
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

    @Query(nativeQuery = true, value = "SELECT mst_release.* FROM mst_release inner join mst_project on mst_release.project_id  = mst_project.id where mst_project.id like %:projectId% and mst_release.status like %:status% and mst_release.stage like %:stage%")
    public List<Release> getReleasebyId2(@Param("projectId") String projectId,@Param("status") String status,@Param("stage") String stage);
}
