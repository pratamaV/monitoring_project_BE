package com.askrindo.service;

import com.askrindo.entity.Division;

import java.util.List;

public interface DivisionService {
    public void saveDivision(Division division);
    public List<Division> getAllDivision();
    public Division getDivisionById(String id);
    public void deleteDivisionById(String id);
    public Division getDivisionByName(String name);
}
