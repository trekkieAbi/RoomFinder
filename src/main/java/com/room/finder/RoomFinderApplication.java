package com.room.finder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@ComponentScan("com.room.finder.mapper")
//@MapperScan("com.room.finder.mapper.*")
@ComponentScan(basePackages = {"com.room.finder.*"})

@SpringBootApplication
public class RoomFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomFinderApplication.class, args);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
