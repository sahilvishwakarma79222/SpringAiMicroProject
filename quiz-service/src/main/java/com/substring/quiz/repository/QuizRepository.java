package com.substring.quiz.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.substring.quiz.collection.Quiz;

public interface QuizRepository extends MongoRepository<Quiz, String>{

	List<Quiz> findByCategoryId(String categoryId);
}
