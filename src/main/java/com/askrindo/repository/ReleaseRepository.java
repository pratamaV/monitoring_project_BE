package com.askrindo.repository;

import com.askrindo.entity.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by DELL on 26/02/2021.
 */

@Repository
public interface ReleaseRepository extends JpaRepository<Release, String> {
}
