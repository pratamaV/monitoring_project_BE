package com.askrindo.service;

import com.askrindo.entity.Issued;
import com.askrindo.repository.IssuedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */

@Service
public class IssuedServiceImpl implements IssuedService {

    @Autowired
    IssuedRepository issuedRepository;

    @Override
    public void saveIssued(Issued issued) {
        issuedRepository.save(issued);
    }

    @Override
    public void changeStatusIssue(String id) {
        Issued issued = issuedRepository.findById(id).get();
        if (issued.getStatus().equals("Open")){
            issued.setStatus("Close");
        } else if (issued.getStatus().equals("Close")){
            issued.setStatus("Open");
        }
        issuedRepository.save(issued);
    }

    @Override
    public List<Issued> getAllIssued() {
        return issuedRepository.findAll();
    }

    @Override
    public Issued getIssuedById(String id) {
        return issuedRepository.findById(id).get();
    }

    @Override
    public void updateIssued(Issued issued) {
        issuedRepository.save(issued);
    }

    @Override
    public List<Issued> getIssuedByReleaseId(String id) {
        return issuedRepository.findIssuedByReleaseId(id);
    }
}
