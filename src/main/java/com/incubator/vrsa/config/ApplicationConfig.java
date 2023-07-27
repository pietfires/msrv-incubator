package com.incubator.vrsa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public String successMessage(){
        return "This is an injected success message from a Bean in a config.";
    }
}
