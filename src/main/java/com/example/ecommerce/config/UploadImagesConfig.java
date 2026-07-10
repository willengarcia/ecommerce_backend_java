package com.example.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class UploadImagesConfig implements WebMvcConfigurer {
    @Value("${app.upload.directory}")
    private String uploadDirectory;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String caminhoAbsoluto = "file:"
                + System.getProperty("user.dir")
                + "/"
                + uploadDirectory
                + "/";

        registry
                .addResourceHandler("/uploads/products/**")
                .addResourceLocations(caminhoAbsoluto);
    }
}
