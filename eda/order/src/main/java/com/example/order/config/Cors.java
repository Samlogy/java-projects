package com.example.demo2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Cors implements WebMvcConfigurer {
    
//  @Override
//   public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOrigins("*")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("*");
//        // registry.addMapping("/**")
//        //         .allowedOrigins("http://localhost:4200") // Update with your Angular app's URL
//        //         .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//        //         .allowedHeaders("*")
//        //         .allowCredentials(true)
//        //         .maxAge(3600);
//   }
}

