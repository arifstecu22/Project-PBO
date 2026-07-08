package com.tokokue.demo.controller; // Sesuaikan dengan package proyekmu

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Menghubungkan URL /images/** langsung ke folder fisik di laptop
        String userDir = System.getProperty("user.dir");
        String pathFoto = Paths.get(userDir, "src", "main", "resources", "static", "images").toUri().toString();
        
        registry.addResourceHandler("/images/**")
                .addResourceLocations(pathFoto);
    }
}