package com.askrindo.repository;

import com.askrindo.entity.Issued;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by DELL on 26/02/2021.
 */

@Repository
public interface IssuedRepository extends JpaRepository<Issued, String> {
}
