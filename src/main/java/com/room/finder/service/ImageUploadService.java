package com.room.finder.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ImageUploadService {
    public Map<String,String> uploadImage(String path, MultipartFile file) throws IOException;
}
