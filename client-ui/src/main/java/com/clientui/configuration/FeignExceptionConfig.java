package com.clientui.configuration;

import com.clientui.exceptions.CustomErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

///cette classe peut etre remplacee par l annotation @Component sur la classe CustomErrorDecoder qui
//serait alors declaree comme ici comme un Bean

@Configuration
public class FeignExceptionConfig {

    @Bean
    public CustomErrorDecoder methodCustomErrorDecoder(){
        return new CustomErrorDecoder();
    }
}
