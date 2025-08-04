package com.substring.quiz.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.substring.quiz.dto.QuizDto;
import com.substring.quiz.service.QuizService;

@RestController
@RequestMapping("/api/v1/quizzes")
public class QuizController {

	private final QuizService quizService;
	
	public QuizController(QuizService quizService) {
		this.quizService=quizService;
	}
	
	@PostMapping("/save")
	public ResponseEntity<QuizDto> saveQuiz(@RequestBody QuizDto dto){
		return new ResponseEntity<QuizDto>(quizService.saveQuiz(dto),HttpStatus.OK);
	}
	
	   @GetMapping("/all")
	    public ResponseEntity<List<QuizDto>> getAllQuizzes() {
	        List<QuizDto> quizzes = quizService.allQuiz();
	        return new ResponseEntity<>(quizzes, HttpStatus.OK);
	    }
	
	   @GetMapping("/{quizId}")
	    public ResponseEntity<QuizDto> getQuizById(@PathVariable String quizId) {
	        QuizDto quiz = quizService.getQuizById(quizId);
	        return new ResponseEntity<>(quiz, HttpStatus.OK);
	    }
	   
	   @GetMapping("/category/{categoryId}")
	    public ResponseEntity<List<QuizDto>> getQuizByCategoryId(@PathVariable String categoryId) {
	        List<QuizDto> dto = quizService.getByCategoryId(categoryId);
	        return new ResponseEntity<>(dto, HttpStatus.OK);
	    }
	   
	    @PutMapping("/{quizId}")
	    public ResponseEntity<QuizDto> updateQuiz(@PathVariable String quizId, @RequestBody QuizDto dto) {
	        QuizDto updatedQuiz = quizService.updateQuiz(quizId, dto);
	        return new ResponseEntity<>(updatedQuiz, HttpStatus.OK);
	    }
	
	    @DeleteMapping("/{quizId}")
	    public ResponseEntity<String> deleteQuiz(@PathVariable String quizId) {
	        String message = quizService.deleteQuiz(quizId);
	        return new ResponseEntity<>(message, HttpStatus.OK);
	    }
	
	
}
