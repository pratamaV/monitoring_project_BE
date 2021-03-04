package com.askrindo.service;

import com.askrindo.entity.sequence.SequenceIdProject;
import com.askrindo.repository.SequenceIdProjectRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SequenceIdProjectServiceImpl implements SequenceIdProjectService {

    @Autowired
    SequenceIdProjectRepositoy sequenceIdProjectRepositoy;


    @Override
    public SequenceIdProject saveSequenceIdProject(SequenceIdProject sequenceIdProject) {
        return sequenceIdProjectRepositoy.save(sequenceIdProject);
    }
}
