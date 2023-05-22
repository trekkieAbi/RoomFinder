package com.room.finder.service.impl;

import com.room.finder.util.RoomInformation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.MANDATORY)
public class ImageUploadService implements com.room.finder.service.ImageUploadService {


    @Override
    public void uploadImage(String path, MultipartFile file,String fullPath) throws IOException {
        File f=new File(path);
        if(!f.exists()){
            f.mkdir();
        }
       // Map<String,RoomInformation> message1=getInformationAboutRoom(path, file);

            Files.copy(file.getInputStream(), Paths.get(fullPath));



    }

    @Override
    public Map<String, RoomInformation> getInformationAboutRoom(String path, MultipartFile multipartFile) {
        Map<String,RoomInformation> message=new HashMap<>();
        String fileName= multipartFile.getOriginalFilename();
        String uniqueName= UUID.randomUUID().toString();
        String uniqueFileName=uniqueName.concat(fileName.substring(fileName.lastIndexOf(".")));
        int end=fileName.indexOf(".");
        String roomName=fileName.substring(0,end);
        String fullPath=path+ File.separator+uniqueFileName;
        message.put(uniqueFileName,new RoomInformation(roomName,fullPath));
        return message;
    }
}
