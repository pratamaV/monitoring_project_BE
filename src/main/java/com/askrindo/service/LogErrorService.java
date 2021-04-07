package com.askrindo.service;

import com.askrindo.entity.Issued;
import com.askrindo.entity.LogError;

import java.util.List;

public interface LogErrorService {
    public void saveLogError(LogError logError);
    public List<LogError> getAllLogError();
    public LogError getLogErrorById(String id);
    public void updateLogError(LogError logError);
}
