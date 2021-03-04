package com.askrindo.service;

import com.askrindo.entity.sequence.SequenceIdTask;
import com.askrindo.repository.SequenceIdTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SequenceIdTaskServiceImpl implements SequenceIdTaskService {

    @Autowired
    SequenceIdTaskRepository sequenceIdTaskRepository;

    @Override
    public SequenceIdTask saveSequenceIdTask(SequenceIdTask sequenceIdTask) {
        return sequenceIdTaskRepository.save(sequenceIdTask);
    }
}
