package com.substring.quiz.category.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.substring.quiz.category.dto.CategoryDto;
import com.substring.quiz.category.dto.PageResponse;
import com.substring.quiz.category.service.CategoryService;

@RequestMapping("/api/v1/category")
@RestController
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
//-------------------------------------------------------------------------------------------------------------
    @Value("${config.value}")
    private String configValue;

    @GetMapping("/config")
    public ResponseEntity<String> getConfigValue(){
        return new ResponseEntity<>(configValue,HttpStatus.OK);
    }

//-------------------------------------------------------------------------------------------------------------


	@PostMapping("/create")
	public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto dto) {

		return new ResponseEntity<CategoryDto>(categoryService.saveCategory(dto), HttpStatus.OK);
	}

	@GetMapping("/catId/{categoryId}")
	public ResponseEntity<?> getCategoryById(@PathVariable String categoryId) {
		Optional<CategoryDto> category = categoryService.findCategoryById(categoryId);

		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	@GetMapping("/all/page")
	public ResponseEntity<?> allCategoryByPagination(
			@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
			@RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
			@RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
			PageResponse<CategoryDto> allCategories = categoryService.getAllCategories(pageNumber, pageSize, sortBy,
				sortDir);
			return new ResponseEntity<>(allCategories, HttpStatus.OK);
	}
	@GetMapping("/all")
	public ResponseEntity<List<CategoryDto>> getAll(){
		List<CategoryDto> all = categoryService.findAll();
		return new ResponseEntity<List<CategoryDto>>(all,HttpStatus.OK);
	}
	

	@PutMapping("/category/update/{categoryId}")
	public ResponseEntity<?> updateCategory(@PathVariable String categoryId, @RequestBody CategoryDto dto) {
		CategoryDto updateCategory = categoryService.updateCategory(categoryId, dto);
		return new ResponseEntity<>(updateCategory, HttpStatus.OK);
	}
	
	

	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable String categoryId) {
		String msg = categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}




}
