package com.example.demo.Config;


import org.modelmapper.ModelMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableScheduling
public class WebAppConfig implements WebMvcConfigurer {

    
    @Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:4200").
		allowCredentials(true).allowedMethods("*");
	
		
    }
    
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	

}


