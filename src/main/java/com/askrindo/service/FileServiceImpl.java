package com.askrindo.service;

import com.askrindo.entity.File;
import com.askrindo.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileRepository fileRepository;

    @Override
    public void saveFile(File file) {
        fileRepository.save(file);
    }

    @Override
    public void deleteFile(String id) {
        fileRepository.deleteById(id);
    }

    @Override
    public File getFileById(String id) {
        return fileRepository.findById(id).get();
    }

    @Override
    public Integer countFileByIdTask(String taskId) {
        return fileRepository.countFileByTaskId(taskId);
    }
}
