package com.substring.quiz.question.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.substring.quiz.question.dto.QuizDto;

@FeignClient(name="question-service",url = "http://localhost:9092/api/v1/quizzes")
public interface QuizFeignService {

	
	@GetMapping("/{quizId}")
	public QuizDto getByQuizId(@PathVariable String quizId);
	
	
	
}
