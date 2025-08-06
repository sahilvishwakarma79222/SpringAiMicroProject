package com.substring.quiz.question.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.substring.quiz.question.dto.PageResponse;
import com.substring.quiz.question.dto.QuestionDto;
import com.substring.quiz.question.service.QuestionService;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {

	private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/create")
    public ResponseEntity<QuestionDto> saveQuestion(@RequestBody QuestionDto dto) {
        return new ResponseEntity<>(questionService.saveQuestion(dto), HttpStatus.OK);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long questionId) {
        Optional<QuestionDto> question = questionService.findQuestionById(questionId);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @GetMapping("/all/page")
    public ResponseEntity<PageResponse<QuestionDto>> getAllQuestions(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        PageResponse<QuestionDto> response = questionService.getAllQuestions(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuestionDto>> getAll() {
        List<QuestionDto> all = questionService.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PutMapping("/update/{questionId}")
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable Long questionId, @RequestBody QuestionDto dto) {
        QuestionDto updated = questionService.updateQuestion(questionId, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long questionId) {
        String msg = questionService.deleteQuestion(questionId);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
	
}
