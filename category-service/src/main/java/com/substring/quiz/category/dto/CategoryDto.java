package com.substring.quiz.category.dto;

import org.springframework.stereotype.Service;

import lombok.Data;


@Data
public class CategoryDto {

	

	private String id;
	
	private String title;
	
	private String description;
	
	private boolean active;
	
}
