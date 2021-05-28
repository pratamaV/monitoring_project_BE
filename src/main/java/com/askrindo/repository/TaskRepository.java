package com.askrindo.repository;

import com.askrindo.entity.Release;
import com.askrindo.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Integer countTaskByReleaseId(String idRelease);
    public Page<Task> findAllByReleaseId(String idRelease, Pageable pageable);
    public Page<Task> findAllByAssignedToId(String idUser, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from mst_task where release_id = :id order by est_start_date ASC limit 1")
    public Task findTaskByEstStartDateAcs(@Param("id") String id);

    @Query(nativeQuery = true, value = "select * from mst_task where release_id = :id order by est_end_date DESC limit 1")
    public Task findTaskByEstEndDateDesc(@Param("id") String id);

    @Query(nativeQuery = true, value = "select * from mst_task where release_id = :id order by act_start_date ASC limit 1")
    public Task findTaskByActStartDateAcs(@Param("id") String id);

    @Query(nativeQuery = true, value = "select * from mst_task where release_id = :id order by act_end_date DESC limit 1")
    public Task findTaskByActEndDateDesc(@Param("id") String id);

    @Query(nativeQuery = true, value = "select mst_task.* from mst_task inner join mst_release on mst_task.release_id = mst_release.id inner join mst_user on mst_task.assigned_to = mst_user.id where mst_release.id like %:releaseId% and mst_user.id like %:userId% and mst_task.status_done like %:statusDone% order by mst_task.task_code asc")
    public Page<Task> getTaskByReleaseId(@Param("releaseId") String releaseId, @Param("userId") String userId, @Param("statusDone") String statusDone, Pageable pageable);

    @Query(nativeQuery = true, value = "select mst_task.* from mst_task inner join mst_release on mst_task.release_id = mst_release.id inner join mst_user on mst_task.assigned_to = mst_user.id inner join mst_project on mst_release.project_id = mst_project.id where mst_task.assigned_to like %:id% and mst_task.status_done like %:statusDone% and mst_release.release_name like %:releaseName% and mst_project.project_name like %:projectName% order by mst_task.task_code asc")
    public Page<Task> getTaskAssignedToId(@Param("id") String id, @Param("statusDone") String statusDone, @Param("releaseName") String releaseName,
                                          @Param("projectName") String projectName, Pageable pageable);

    @Query(nativeQuery = true, value = "select mst_task.* from mst_task inner join mst_release on mst_task.release_id = mst_release.id inner join mst_user on mst_task.assigned_to = mst_user.id inner join mst_project on mst_release.project_id = mst_project.id where mst_task.assigned_to like %:id% and mst_task.status_done like %:statusDone% and mst_release.release_name like %:releaseName% and mst_project.project_name like %:projectName% and (mst_task.est_start_date >= :estStartDateFrom and mst_task.est_start_date <= :estStartDateTo) and (mst_task.est_end_date >= :estEndDateFrom and mst_task.est_end_date <= :estEndDateTo) order by mst_task.task_code asc")
    public Page<Task> getTaskAssignedToIdWithDate(@Param("id") String id, @Param("statusDone") String statusDone, @Param("releaseName") String releaseName,
                                                  @Param("projectName") String projectName, @Param("estStartDateFrom") Date estStartDateFrom, @Param("estStartDateTo") Date estStartDateTo, @Param("estEndDateFrom") Date estEndDateFrom, @Param("estEndDateTo") Date estEndDateTo, Pageable pageable);

    @Query(nativeQuery = true, value = "select mst_task.* from mst_task inner join mst_release on mst_task.release_id = mst_release.id inner join mst_user on mst_task.assigned_to = mst_user.id inner join mst_project on mst_release.project_id = mst_project.id where mst_task.assigned_to like %:id% and mst_task.status_done like %:statusDone% and mst_release.release_name like %:releaseName% and mst_project.project_name like %:projectName% and (mst_task.est_start_date >= :estStartDateFrom and mst_task.est_start_date <= :estStartDateTo) order by mst_task.task_code asc ")
    public Page<Task> getTaskAssignedToIdWithEstStartDate(@Param("id") String id, @Param("statusDone") String statusDone, @Param("releaseName") String releaseName,
                                                          @Param("projectName") String projectName, @Param("estStartDateFrom") Date estStartDateFrom, @Param("estStartDateTo") Date estStartDateTo, Pageable pageable);

    @Query(nativeQuery = true, value = "select mst_task.* from mst_task inner join mst_release on mst_task.release_id = mst_release.id inner join mst_user on mst_task.assigned_to = mst_user.id inner join mst_project on mst_release.project_id = mst_project.id where mst_task.assigned_to like %:id% and mst_task.status_done like %:statusDone% and mst_release.release_name like %:releaseName% and mst_project.project_name like %:projectName% and (mst_task.est_end_date >= :estEndDateFrom and mst_task.est_end_date <= :estEndDateTo) order by mst_task.task_code asc")
    public Page<Task> getTaskAssignedToIdWithEstEndDate(@Param("id") String id, @Param("statusDone") String statusDone, @Param("releaseName") String releaseName,@Param("projectName") String projectName,
                                                        @Param("estEndDateFrom") Date estEndDateFrom, @Param("estEndDateTo") Date estEndDateTo, Pageable pageable);

    @Query(nativeQuery = true, value = "select mst_task.* from mst_task where est_end_date < now() and status_done not like '%DONE%' order by est_end_date asc")
    public List<Task> findTaskDeadline();

}
