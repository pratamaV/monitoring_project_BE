package com.askrindo.service;

import com.askrindo.entity.LogError;
import com.askrindo.repository.LogErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogErrorServiceImpl implements LogErrorService {
    @Autowired
    LogErrorRepository logErrorRepository;

    @Override
    public void saveLogError(LogError logError) {
        logErrorRepository.save(logError);
    }

    @Override
    public List<LogError> getAllLogError() {
        return logErrorRepository.findAll();
    }

    @Override
    public LogError getLogErrorById(String id) {
        return logErrorRepository.findById(id).get();
    }

    @Override
    public void updateLogError(LogError logError) {
        logErrorRepository.save(logError);
    }
}
