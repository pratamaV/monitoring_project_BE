package com.askrindo.service;

import com.askrindo.entity.Division;
import com.askrindo.exception.DataNotFoundException;
import com.askrindo.repository.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class DivisionServiceImpl implements DivisionService {

    @Autowired
    DivisionRepository divisionRepository;

    @Override
    public void saveDivision(Division division) {
        divisionRepository.save(division);
    }

    @Override
    public List<Division> getAllDivision() {
        return divisionRepository.findAll();
    }

    @Override
    public Division getDivisionById(String id) {
        return divisionRepository.findById(id).get();
    }

    @Override
    public void deleteDivisionById(String id) {
        divisionRepository.deleteById(id);
    }

    @Override
    public Division getDivisionByName(String name) {
        System.out.println("nameee: " + name);
        Optional<Division> divisionOptional = divisionRepository.findDivisionByDivisionName(name);
        System.out.println("divisionOptaional: " + divisionOptional);
        if(divisionOptional.isPresent()){
            return divisionOptional.get();
        }
        return null;
    }

}
