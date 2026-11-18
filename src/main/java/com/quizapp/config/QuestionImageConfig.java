package com.quizapp.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class QuestionImageConfig implements WebMvcConfigurer {

    @Value("${quiz.upload-dir:uploads/question-images}")
    private String uploadDirectory;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        Path imageDirectory = Paths.get(uploadDirectory)
                .toAbsolutePath()
                .normalize();

        String location = imageDirectory.toUri().toString();

        registry.addResourceHandler("/question-images/**")
                .addResourceLocations(location);
    }
}
