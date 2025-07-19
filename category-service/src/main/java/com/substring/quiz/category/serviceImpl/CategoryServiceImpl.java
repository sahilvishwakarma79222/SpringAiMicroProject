package com.substring.quiz.category.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.substring.quiz.category.dto.CategoryDto;
import com.substring.quiz.category.dto.PageResponse;
import com.substring.quiz.category.entities.Category;
import com.substring.quiz.category.repositories.CategoryRepository;
import com.substring.quiz.category.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	private final CategoryRepository categoryRepository;
	private final ModelMapper mapper;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository,ModelMapper mapper) {
		this.mapper=mapper;
		this.categoryRepository=categoryRepository;
	}
	
	
	@Override
	public CategoryDto saveCategory(CategoryDto dto) {

		Category entity = mapper.map(dto, Category.class);
		String randomId=UUID.randomUUID().toString();
		entity.setId(randomId);
		Category save = categoryRepository.save(entity);
		return mapper.map(save, CategoryDto.class);
	}


	@Override
	public PageResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortByField,
			String sortDirection) {
		Sort  sort=Sort.by(sortByField).descending();
		if(sortDirection.equals("asc")) {
			sort=Sort.by(sortByField).ascending();
		}
		PageRequest pagereq = PageRequest.of(pageNumber, pageSize,sort);

		Page<Category> page = categoryRepository.findAll(pagereq);
		List<Category> entitylist = page.getContent();
		List<CategoryDto> categoryDto = entitylist.stream().map(e->mapper.map(e, CategoryDto.class)).collect(Collectors.toList());
		
		PageResponse<CategoryDto> response=new PageResponse<CategoryDto>();
		response.setContent(categoryDto);
		response.setLast(page.isLast());
		response.setPageSize(pageSize);
		response.setTotalElement(page.getTotalElements());
		response.setPageNumber(pageNumber);
		return response;
	}


	@Override
	public Optional<CategoryDto> findCategoryById(String catId) {
		categoryRepository.findById(catId).orElseThrow(()-> new RuntimeException("category not found with id "+catId));
		Optional<Category> category = categoryRepository.findById(catId);
		Optional<CategoryDto> singleCategory = category.map(c->mapper.map(c, CategoryDto.class));
		return singleCategory;
	}


	@Override
	public CategoryDto updateCategory(String catId,CategoryDto dto) {
		categoryRepository.findById(catId).orElseThrow(()-> new RuntimeException("category not found with id "+catId));
		Category entity = mapper.map(dto, Category.class);
		entity.setId(catId);
		Category cat = categoryRepository.save(entity);
		CategoryDto responseDto = mapper.map(cat, CategoryDto.class);
		return responseDto;
	}


	@Override
	public String deleteCategory(String catId) {
		categoryRepository.findById(catId).orElseThrow(()-> new RuntimeException("category not found with id "+catId));
		categoryRepository.deleteById(catId);
		
		return "category is succesfully deleted with id "+catId;
	}

	

	
	
	
}
