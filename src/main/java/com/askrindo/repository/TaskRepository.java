package com.askrindo.repository;

import com.askrindo.entity.Release;
import com.askrindo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    public List<Task> findTaskByReleaseId(String id);
    public List<Task> findTaskByAssignedToId(String id);

    @Query(nativeQuery = true, value = "select * from mst_task order by est_start_date ASC limit 1")
    public Task findTaskByEstStartDateAcs();

    @Query(nativeQuery = true, value = "select * from mst_task order by est_end_date DESC limit 1")
    public Task findTaskByEstEndDateDesc();

    @Query(nativeQuery = true, value = "select * from mst_task order by act_start_date ASC limit 1")
    public Task findTaskByActStartDateAcs();

    @Query(nativeQuery = true, value = "select * from mst_task order by act_end_date DESC limit 1")
    public Task findTaskByActEndDateDesc();

    @Query(nativeQuery = true, value = "select mst_task.* from mst_task inner join mst_release on mst_task.release_id = mst_release.id inner join mst_user on mst_task.assigned_to = mst_user.id where mst_release.id like %:releaseId% and mst_user.id like %:userId% and mst_task.status_done like %:statusDone%")
    public List<Task> getTaskByReleaseId(@Param("releaseId") String releaseId, @Param("userId") String userId, @Param("statusDone") String statusDone);

    @Query(nativeQuery = true, value = "select mst_task.* from mst_task inner join mst_release on mst_task.release_id = mst_release.id inner join mst_user on mst_task.assigned_to = mst_user.id inner join mst_project on mst_release.project_id = mst_project.id where mst_task.assigned_to like %:id% and mst_task.status_done like %:statusDone% and mst_release.release_name like %:releaseName% and mst_project.project_name like %:projectName% and to_char(mst_task.est_start_date, 'YYYY-MM-DD') like %:estStartDate% and to_char(mst_task.est_end_date, 'YYYY-MM-DD') like %:estEndDate%")
    public List<Task> getTaskAssignedToId(@Param("id") String id, @Param("statusDone") String statusDone, @Param("releaseName") String releaseName,
                                          @Param("projectName") String projectName, @Param("estStartDate") String estStartDate, @Param("estEndDate") String estEndDate);
}
