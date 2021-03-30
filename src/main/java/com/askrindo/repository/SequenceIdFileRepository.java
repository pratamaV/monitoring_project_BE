package com.askrindo.repository;

import com.askrindo.entity.sequence.SequenceIdFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SequenceIdFileRepository extends JpaRepository<SequenceIdFile, String> {
}
