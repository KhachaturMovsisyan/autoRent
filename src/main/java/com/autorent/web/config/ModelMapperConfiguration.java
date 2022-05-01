
package com.autorent.web.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

}