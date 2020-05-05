package com.bookstore;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.bookstore.service.IBookService;

@SpringBootApplication
public class BookStoreApplication{

	@Resource
	IBookService bookservice;
	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}
	
	/*
	 * Method for RestTemplate Instance. It's Inject RestTempate Object. 
	 **/
	@Bean
	public RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}
	
}
