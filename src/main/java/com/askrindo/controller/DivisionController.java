package com.askrindo.controller;

import com.askrindo.entity.Division;
import com.askrindo.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DivisionController {

    @Autowired
    DivisionService divisionService;

    @PostMapping("/division")
    public void saveDivision(@RequestBody Division division){
        divisionService.saveDivision(division);
    }

    @PostMapping("/divisions")
    public void saveDivisionArray(@RequestBody List<Division> division){
        for (Division division1: division) {
            divisionService.saveDivision(division1);
        }
    }

    @GetMapping("/divisions")
    public List<Division> getAllDivision(){
        return divisionService.getAllDivision();
    }

    @GetMapping("/division/{id}")
    public Division getDivisionById(@PathVariable String id){
        return divisionService.getDivisionById(id);
    }

    @GetMapping("/division")
    public Division getDivisionByName(@RequestParam String division){
        return divisionService.getDivisionByName(division);
    }

    @DeleteMapping("/division/{id}")
    public void deleteDivisionById(@PathVariable String id){
        divisionService.deleteDivisionById(id);
    }
}
