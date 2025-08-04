package com.substring.quiz.service;

import java.util.List;

import com.substring.quiz.dto.CategoryDto;

public interface CategorySerrvice {

	CategoryDto findById(String categoryId);
	List<CategoryDto> finaAll();
	CategoryDto create(CategoryDto categoryDto);
	CategoryDto update(String categoryId,CategoryDto categoryDto);
	void delete(String categoryId);
	
}
