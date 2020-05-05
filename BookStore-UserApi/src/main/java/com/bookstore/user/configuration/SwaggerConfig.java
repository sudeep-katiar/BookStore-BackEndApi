package com.bookstore.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/*****************************************************************************************************
 * SwaggerWeb Appliaction Configuration For Project Controller Paths. 
 *  
 * @author Ritesh Patil
 * @version 1.0
 * @created 2020-04-11
 * 
 ******************************************************************************************************/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.bookstore.user.controller")).paths(regex("/users.*"))
				.build();
	}
}