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
}
