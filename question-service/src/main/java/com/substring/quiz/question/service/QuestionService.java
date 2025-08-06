package com.substring.quiz.question.service;

import java.util.List;
import java.util.Optional;

import com.substring.quiz.question.dto.PageResponse;
import com.substring.quiz.question.dto.QuestionDto;

public interface QuestionService {

    QuestionDto saveQuestion(QuestionDto dto);

    PageResponse<QuestionDto> getAllQuestions(int pageNumber, int pageSize, String sortByField, String sortDirection);

    List<QuestionDto> findAll();

    Optional<QuestionDto> findQuestionById(Long questionId);

    QuestionDto updateQuestion(Long questionId, QuestionDto dto);

    String deleteQuestion(Long questionId);
}
