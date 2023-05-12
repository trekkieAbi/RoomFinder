package com.room.finder.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageUploadService implements com.room.finder.service.ImageUploadService {

    @Override
    public Map<String,String> uploadImage(String path, MultipartFile file) throws IOException {
        String fileName= file.getOriginalFilename();
        Map<String,String> message=new HashMap<>();

        String uniqueName= UUID.randomUUID().toString();
        String uniqueFileName=uniqueName.concat(fileName.substring(fileName.lastIndexOf(".")));
        String fullPath=path+ File.separator+uniqueFileName;
        File f=new File(path);
        if(!f.exists()){
            f.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(fullPath));
        message.put(uniqueFileName,fullPath);
        return message;
    }
}
