package com.company.addressapp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddressConfig {

    @Bean
    public ModelMapper modelMapperFactoryMethod(){
        return new ModelMapper();
    }
}
