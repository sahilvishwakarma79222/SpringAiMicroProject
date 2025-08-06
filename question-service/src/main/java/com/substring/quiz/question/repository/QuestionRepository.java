package com.substring.quiz.question.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.substring.quiz.question.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{

    List<Question> findByQuizId(String quizId);

    List<Question> findByQuizIdAndActiveTrue(String quizId);

}
