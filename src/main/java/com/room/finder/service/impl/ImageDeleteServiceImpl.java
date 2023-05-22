package com.room.finder.service.impl;

import com.room.finder.service.ImageDeleteService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageDeleteServiceImpl implements ImageDeleteService {
    private final Path root= Paths.get("images/");

    @Override
    public boolean deleteImage(String fileName) {
        try{
            Path file=root.resolve(fileName);
            System.out.println(file.toUri());
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            e.getLocalizedMessage();
            throw new RuntimeException("Error occured while deleting file ");
        }
    }
}
