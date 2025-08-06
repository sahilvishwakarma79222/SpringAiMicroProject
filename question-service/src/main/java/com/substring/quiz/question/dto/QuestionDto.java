package com.substring.quiz.question.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {

    private Long id;

    private String questionText;

    private String option1;
    private String option2;
    private String option3;
    private String option4;

    private String correctAnswer;

    private String givenAnswer;

    private String quizId;

    private String imageUrl;

    private Integer marks;

    private Boolean active;
    
    private QuizDto quizDto;
}
