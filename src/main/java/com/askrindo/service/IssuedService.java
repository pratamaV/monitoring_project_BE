package com.askrindo.service;

import com.askrindo.entity.Issued;

import java.util.List;

/**
 * Created by DELL on 26/02/2021.
 */
public interface IssuedService {
    public void saveIssued(Issued issued);
    public List<Issued> getAllIssued();
    public Issued getIssuedById(String id);
    public void updateIssued(Issued issued);
    public List<Issued> getIssuedByReleaseId(String id);

    public void changeStatusIssue(String id);
}
