package com.substring.quiz.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@org.springframework.context.annotation.Configuration
public class Configuration {

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}	
	
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
//	this is used jab humara base url change hota rhega 
//	@Bean
//	public WebClient.Builder  webClient() {
//		return WebClient.builder();
//	}
	
	@Bean
	public WebClient webClient() {
		return WebClient.builder()
				.baseUrl("http://localhost:9091")
				.build();
	}
}	
