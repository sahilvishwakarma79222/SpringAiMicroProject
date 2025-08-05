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

	@GetMapping("/all")
	List<CategoryDto> findAll();	
	
	@GetMapping("/catId/{categoryId}")
	CategoryDto findByCategoryId(@PathVariable String categoryId);
	
	@PostMapping("/create")
	CategoryDto create(@RequestBody CategoryDto categoryDto);
	
	@PutMapping("/category/update/{categoryId}")
	CategoryDto update(@PathVariable String categoryId,@RequestBody CategoryDto categoryDto);
	
	@DeleteMapping("/delete/{categoryId}")
	String delete (@PathVariable String categoryId);
	
}
