package com.substring.quiz.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.substring.quiz.dto.CategoryDto;

@FeignClient(name = "CATEGORY-SERVICE"
//,url = "http://CATEGORY-SERVICE/api/v1/category"
)
public interface CategoryFeignService {

	@GetMapping("/api/v1/category/all")
	List<CategoryDto> findAll();

	@GetMapping("/api/v1/category/catId/{categoryId}")
	CategoryDto findByCategoryId(@PathVariable String categoryId);

	@PostMapping("/api/v1/category/create")
	CategoryDto create(@RequestBody CategoryDto categoryDto);

	@PutMapping("/api/v1/category/category/update/{categoryId}")
	CategoryDto update(@PathVariable String categoryId,@RequestBody CategoryDto categoryDto);

	@DeleteMapping("/api/v1/category/delete/{categoryId}")
	String delete (@PathVariable String categoryId);

}
