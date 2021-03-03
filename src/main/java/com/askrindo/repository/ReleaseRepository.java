package com.askrindo.repository;

import com.askrindo.entity.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */

@Repository
public interface ReleaseRepository extends JpaRepository<Release, String> {
    public List<Release> findReleaseByProjectId(String id);
    public List<Release> findReleaseByStage(String stage);
    public List<Release> findReleaseByStatus(String status);
}
