package com.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
/*****************************************************************************************************
 * FreeMarker Configuration Class Which Inject Object Of FreeMarkerConfigurationFactoryBean.
 *  
 * @author Rupesh Patil
 * @version 1.0
 * @created 2020-04-13
 * 
 ******************************************************************************************************/

@Configuration
public class FreeMarkerConfig {

	@Primary
	@Bean
	public FreeMarkerConfigurationFactoryBean factoryBean() {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPath("classpath:/template");
		return bean;
	}
}
