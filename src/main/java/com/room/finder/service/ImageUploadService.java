package com.room.finder.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUploadService {
    public String uploadImage(String path, MultipartFile file) throws IOException;
}
