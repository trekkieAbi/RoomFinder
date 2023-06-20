package com.room.finder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Date;



@SpringBootApplication
public class RoomFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomFinderApplication.class, args);
        System.out.println(new Date(System.currentTimeMillis()+(5*60*60*1000)));
    }
   
}
