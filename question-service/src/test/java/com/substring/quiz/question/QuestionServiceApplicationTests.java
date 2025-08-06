package com.substring.quiz.question;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.substring.quiz.question.dto.QuestionDto;
import com.substring.quiz.question.entity.Question;
import com.substring.quiz.question.service.QuestionService;

@SpringBootTest
class QuestionServiceApplicationTests {

	@Autowired
	private QuestionService questionService;
	
	
	@Test
	void testRestQuiz() {
		QuestionDto questionDto = QuestionDto.builder().id(1L)
	    .questionText("What is the capital of France?")
	    .option1("Berlin")
	    .option2("Madrid")
	    .option3("Paris")
	    .option4("Rome")
	    .correctAnswer("Paris")
	    .givenAnswer("Paris")
	    .quizId("747c5c23-fbf0-4c7c-a5c7-d7e8618fa618")
	    .imageUrl("https://example.com/images/paris-question.png")
	    .marks(5)
	    .active(true)
	    .build();
		System.out.println(" quiz is "+questionDto.getQuizId());
		System.out.println("=================== ok =============================");
		questionService.saveQuestion(questionDto);
		System.out.println("=================== ok =============================");

	}
	
	@Test
	void contextLoads() {
	}

	
	
	
	
}
