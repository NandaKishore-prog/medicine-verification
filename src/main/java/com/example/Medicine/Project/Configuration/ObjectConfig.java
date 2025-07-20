package com.example.Medicine.Project.Configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectConfig {
    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }
}
