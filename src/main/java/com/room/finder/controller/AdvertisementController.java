package com.room.finder.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvertisementController {
    @Value("${project.image}")
    private String path;

}
