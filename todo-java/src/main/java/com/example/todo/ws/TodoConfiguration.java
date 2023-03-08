package com.example.todo.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration pour swagger-ui
 * @author hbziouet
 *
 */
@Configuration
@EnableSwagger2
public class TodoConfiguration {     
	
	public static final String WS_TODO_PATH = "/todo";
    @Bean
    Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                                         
          .build();                                           
    }
    
    
}
