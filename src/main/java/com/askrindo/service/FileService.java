package com.askrindo.service;

import com.askrindo.entity.File;

public interface FileService {
    public void saveFile(File file);
    public void deleteFile(String id);
    public File getFileById(String id);
    public Integer countFileByIdTask(String taskId);
}
