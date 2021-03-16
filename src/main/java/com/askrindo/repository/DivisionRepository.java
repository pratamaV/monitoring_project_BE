package com.askrindo.repository;

import com.askrindo.entity.Division;
import com.askrindo.entity.Issued;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DivisionRepository extends JpaRepository<Division, String> {
    public Optional<Division> findDivisionByDivisionName(String id);

}
