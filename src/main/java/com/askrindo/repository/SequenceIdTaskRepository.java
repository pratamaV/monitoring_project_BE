package com.askrindo.repository;

import com.askrindo.entity.sequence.SequenceIdTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceIdTaskRepository extends JpaRepository<SequenceIdTask, String> {
}
