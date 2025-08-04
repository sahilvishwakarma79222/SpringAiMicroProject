package com.substring.quiz.service;

import java.util.List;

import com.substring.quiz.dto.QuizDto;

public interface QuizService {

	QuizDto saveQuiz(QuizDto dto);
	List<QuizDto> allQuiz();
	List<QuizDto> getByCategoryId(String categoryId);
	QuizDto updateQuiz(String quizId,QuizDto dto);
	String deleteQuiz(String quizId);
	QuizDto getQuizById(String quizId);
	
}
