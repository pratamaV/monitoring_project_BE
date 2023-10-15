package com.askrindo.repository;

import com.askrindo.entity.MasterTraining;
import com.askrindo.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterTrainingRepository extends JpaRepository<MasterTraining, String>, JpaSpecificationExecutor<MasterTraining> {

    List<MasterTraining> findAll();

    List<MasterTraining> findAllBySubCompetenceCode(String subCompetenceCode);
}
