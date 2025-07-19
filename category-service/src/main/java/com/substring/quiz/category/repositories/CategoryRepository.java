package com.substring.quiz.category.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.substring.quiz.category.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, String>{

	
}
