package com.substring.quiz;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.substring.quiz.dto.CategoryDto;
import com.substring.quiz.service.CategoryFeignService;

@SpringBootTest
class QuizServiceApplicationTests {

	@Autowired
	private CategoryFeignService categoryFeignService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void testFeignCategoryAll() {
		
		System.out.println("getting all");
		List<CategoryDto> all = categoryFeignService.findAll();
		all.forEach(catdto->
			System.out.println(catdto.getTitle())
				
				);
	}

	@Test
	public void testFeignSingleCategory() {
		System.out.println("gettting single category");
		CategoryDto byCategoryId = categoryFeignService.findByCategoryId("00400fb1-ab54-4fc6-aed8-5d5455135a4f");
		System.out.println(byCategoryId.getTitle());
		
	}
	
	
}
