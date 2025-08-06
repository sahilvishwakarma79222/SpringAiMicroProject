package com.substring.quiz.question.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="questions")
@Getter
@Setter
public class Question {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Column(nullable = false)
    private String option1;

    @Column(nullable = false)
    private String option2;

    @Column(nullable = false)
    private String option3;

    @Column(nullable = false)
    private String option4;

    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;

    @Column(name = "given_answer")
    private String givenAnswer;

    @Column(name = "quiz_id", nullable = false)
    private String quizId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column
    private Integer marks;

    @Column
    private Boolean active;
	
}
