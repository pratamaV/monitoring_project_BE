package com.askrindo.repository;

import com.askrindo.entity.sequence.SequenceIdProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceIdProjectRepository extends JpaRepository<SequenceIdProject, String> {
}
