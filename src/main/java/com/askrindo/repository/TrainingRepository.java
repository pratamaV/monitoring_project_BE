package com.askrindo.repository;

import com.askrindo.entity.MasterTraining;
import com.askrindo.entity.Training;
import com.askrindo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, String>, JpaSpecificationExecutor<Training> {

    List<Training> findAllByIdUsersOrderByLastModifiedByDesc(Users users);

    List<Training> findAllByDivisionCodeOrderByLastModifiedByDesc(String divisionCode);

    List<Training> findAllByTypeOrderByLastModifiedByDesc(String type);
}
