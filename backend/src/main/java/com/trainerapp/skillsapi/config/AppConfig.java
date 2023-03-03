package com.trainerapp.skillsapi.config;


import com.trainerapp.skillsapi.models.Course;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Course course() {
        return new Course();
    }

    // Define other beans here...
}