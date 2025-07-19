package com.substring.quiz.category.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapper {

	
	@Bean
	public org.modelmapper.ModelMapper mapper() {
		return new org.modelmapper.ModelMapper();
	}
	
	
}
