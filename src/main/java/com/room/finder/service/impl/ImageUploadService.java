package com.room.finder.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageUploadService implements com.room.finder.service.ImageUploadService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String fileName= file.getOriginalFilename();

        String uniqueName= UUID.randomUUID().toString();
        String uniqueFileName=uniqueName.concat(fileName.substring(fileName.lastIndexOf(".")));
        String fullPath=path+ File.separator+uniqueFileName;
        File f=new File(path);
        if(!f.exists()){
            f.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(fullPath));
        return fullPath;
    }
}
