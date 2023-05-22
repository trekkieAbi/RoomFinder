package com.room.finder.service;

import com.room.finder.util.RoomInformation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ImageUploadService {
    public void uploadImage(String path, MultipartFile file,String fullPath) throws IOException;
    public Map<String, RoomInformation> getInformationAboutRoom(String path, MultipartFile multipartFile);
}
