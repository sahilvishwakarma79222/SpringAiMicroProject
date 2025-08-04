package com.substring.quiz.serviceimpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.substring.quiz.dto.CategoryDto;
import com.substring.quiz.service.CategorySerrvice;

@Service
public class CategoryServiceImpl implements CategorySerrvice{

	
	Logger logger=LoggerFactory.getLogger(CategoryServiceImpl.class);
	private final RestTemplate restTemplate;
	private final WebClient webClient;
	private ModelMapper mapper;
	
	public CategoryServiceImpl(RestTemplate restTemplate,WebClient webClient,ModelMapper mapper) {
		this.restTemplate=restTemplate;
		this.webClient=webClient;
		this.mapper=mapper;
	}
	
	
	@Override
	public CategoryDto findById(String categoryId) {
		try {
			CategoryDto categoryDto=this.webClient
					.get()
					.uri("/api/v1/category/catId/{category}",categoryId)
					.retrieve()
					.bodyToMono(CategoryDto.class)
					.block();
					return categoryDto;
		} catch (WebClientResponseException e) {
			if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				logger.error("category not found");
			}
			else if(e.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				logger.info("internal server error");
			}
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<CategoryDto> finaAll() {
		
		return this.webClient
		.get()
		.uri("/api/v1/category/all")
		.retrieve()
		.bodyToFlux(CategoryDto.class)
		.collectList()
		.block();
		
	}

	@Override
	public CategoryDto create(CategoryDto categoryDto) {

		return this.webClient.post()
		.uri("/api/v1/category/create")
		.bodyValue(categoryDto)
		.retrieve()
		.bodyToMono(CategoryDto.class)
		.block();
		
		
	}

	@Override
	public CategoryDto update(String categoryId, CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		return this.webClient
		.put()
		.uri("/api/v1/category/category/update/{categoryId}",categoryId)
		.bodyValue(categoryDto)
		.retrieve()
		.bodyToMono(CategoryDto.class)
		.block();
		
	}

	@Override
	public void delete(String categoryId) {

		this.webClient
		.delete()
		.uri("/api/v1/category/delete/{categoryId}",categoryId)
		.retrieve()
		.toBodilessEntity()
		.block();

	}

	
	
	
}
