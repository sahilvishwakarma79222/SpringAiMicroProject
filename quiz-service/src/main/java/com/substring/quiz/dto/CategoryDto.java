package com.substring.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor 
@NoArgsConstructor
@Builder
public class CategoryDto {

	
	private String id;
	
	private String title;
	
	private String description;
	
	private boolean active;

 }
