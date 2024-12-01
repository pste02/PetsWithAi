package com.example.petswithai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class PetsWithAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetsWithAiApplication.class, args);
    }

}
