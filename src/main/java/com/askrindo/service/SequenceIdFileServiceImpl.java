package com.askrindo.service;

import com.askrindo.entity.sequence.SequenceIdFile;
import com.askrindo.repository.SequenceIdFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SequenceIdFileServiceImpl implements SequenceIdFileService {

    @Autowired
    SequenceIdFileRepository sequenceIdFileRepository;

    @Override
    public SequenceIdFile saveSequenceIdFile(SequenceIdFile sequenceIdFile) {
        return sequenceIdFileRepository.save(sequenceIdFile);
    }
}
