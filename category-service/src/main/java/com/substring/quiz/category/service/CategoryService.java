package com.substring.quiz.category.service;

import java.util.List;
import java.util.Optional;

import com.substring.quiz.category.dto.CategoryDto;
import com.substring.quiz.category.dto.PageResponse;

public interface CategoryService {

	public CategoryDto saveCategory(CategoryDto dto);
	public PageResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortByField,
			String sortDirection);
	public Optional<CategoryDto> findCategoryById(String catId);
	public CategoryDto updateCategory(String catId,CategoryDto dto);
	public String deleteCategory(String catId);
	
}
