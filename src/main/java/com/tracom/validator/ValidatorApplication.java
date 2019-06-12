package com.tracom.validator;

import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class ValidatorApplication {

    @Bean
    public JSONObject jsonObject(){
        return new JSONObject();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurerAdapter(){
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/users/login").allowedOrigins("http://localhost:4200");
                registry.addMapping("/users/sign-up").allowedOrigins("http://localhost:4200");
            }
        };
    }
    public static void main(String[] args) {
        SpringApplication.run(ValidatorApplication.class, args);
    }

}
