package com.askrindo.repository;

import com.askrindo.entity.sequence.SequenceIdRelease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceIdReleaseRepository extends JpaRepository<SequenceIdRelease, String> {
}
