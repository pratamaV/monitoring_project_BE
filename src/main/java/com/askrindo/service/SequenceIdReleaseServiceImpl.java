package com.askrindo.service;

import com.askrindo.entity.sequence.SequenceIdRelease;
import com.askrindo.repository.SequenceIdReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SequenceIdReleaseServiceImpl implements SequenceIdReleaseService {

    @Autowired
    SequenceIdReleaseRepository sequenceIdReleaseRepository;

    @Override
    public SequenceIdRelease saveSequenceIdRelease(SequenceIdRelease sequenceIdRelease) {
        return sequenceIdReleaseRepository.save(sequenceIdRelease);
    }
}
