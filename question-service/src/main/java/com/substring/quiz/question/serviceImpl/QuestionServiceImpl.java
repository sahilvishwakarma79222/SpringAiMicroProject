package com.substring.quiz.question.serviceImpl;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.substring.quiz.question.dto.PageResponse;
import com.substring.quiz.question.dto.QuestionDto;
import com.substring.quiz.question.dto.QuizDto;
import com.substring.quiz.question.entity.Question;
import com.substring.quiz.question.repository.QuestionRepository;
import com.substring.quiz.question.service.QuestionService;
import com.substring.quiz.question.service.QuizFeignService;

@Service
public class QuestionServiceImpl implements QuestionService {

	

    private final QuestionRepository questionRepository;
    private final ModelMapper mapper;
    private final RestTemplate restTemplate;
    private final QuizFeignService quizFeignService;
    
    public QuestionServiceImpl(QuestionRepository questionRepository, ModelMapper mapper,
    		RestTemplate restTemplate,
    		QuizFeignService quizFeignService){
        this.questionRepository = questionRepository;
        this.mapper = mapper;
        this.restTemplate=restTemplate;
        this.quizFeignService=quizFeignService;
    
    }

//    @Override
//    public QuestionDto saveQuestion(QuestionDto dto) {
//        Question entity = mapper.map(dto, Question.class);
//        String url="http://localhost:9092/api/v1/quizzes/"+dto.getQuizId();
//        QuizDto quiz = restTemplate.getForObject(url, QuizDto.class);
//        System.out.println("checking quiz");
//        System.out.println(quiz.getTitle());
//        
//        Question saved = questionRepository.save(entity);
//        return mapper.map(saved, QuestionDto.class);
//    }
    
  @Override
  public QuestionDto saveQuestion(QuestionDto dto) {
      Question entity = mapper.map(dto, Question.class);
      String url="http://localhost:9092/api/v1/quizzes/"+dto.getQuizId();

      QuizDto quiz = null;
      try {
          quiz = restTemplate.getForObject(url, QuizDto.class);
          System.out.println("checking quiz");
          System.out.println(quiz.getTitle());
      } catch (HttpClientErrorException.NotFound ex) {
          throw new RuntimeException("Quiz with ID " + dto.getQuizId() + " not found.");
      } catch (HttpClientErrorException ex) {
          throw new RuntimeException("Client error while fetching quiz: " + ex.getStatusCode());
      } catch (ResourceAccessException ex) {
          throw new RuntimeException("Quiz service is unavailable.");
      } catch (Exception ex) {
          throw new RuntimeException("Something went wrong while fetching quiz.", ex);
      }

      Question saved = questionRepository.save(entity);
      return mapper.map(saved, QuestionDto.class);
  }

    @Override
    public List<QuestionDto> findAll() {
        List<Question> list = questionRepository.findAll();
        return list.stream().map(q -> mapper.map(q, QuestionDto.class)).collect(Collectors.toList());
    }

    @Override
    public PageResponse<QuestionDto> getAllQuestions(int pageNumber, int pageSize, String sortByField, String sortDirection) {
        
    	Sort sort = Sort.by(sortByField).descending();
        if ("asc".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(sortByField).ascending();
        }

        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Question> page = questionRepository.findAll(pageable);

        List<QuestionDto> content = page.getContent()
                .stream()
                .map(q -> mapper.map(q, QuestionDto.class))
                .collect(Collectors.toList());

        PageResponse<QuestionDto> response = new PageResponse<>();
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElement(page.getTotalElements());
        response.setContent(content);
        response.setLast(page.isLast());

        return response;
    }

    @Override
    public Optional<QuestionDto> findQuestionById(Long questionId) {
         Optional<Question> question = questionRepository.findById(questionId);
         Optional<QuestionDto> dto = question.map(q -> mapper.map(q, QuestionDto.class));
         QuizDto quizDto = quizFeignService.getByQuizId(question.get().getQuizId());
         dto.get().setQuizDto(quizDto);
         return dto;
    }

    @Override
    public QuestionDto updateQuestion(Long questionId, QuestionDto dto) {
        Question existing = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id " + questionId));

        // Update entity fields
        existing.setQuestionText(dto.getQuestionText());
        existing.setOption1(dto.getOption1());
        existing.setOption2(dto.getOption2());
        existing.setOption3(dto.getOption3());
        existing.setOption4(dto.getOption4());
        existing.setCorrectAnswer(dto.getCorrectAnswer());
        existing.setGivenAnswer(dto.getGivenAnswer());
        existing.setQuizId(dto.getQuizId());
        existing.setImageUrl(dto.getImageUrl());
        existing.setMarks(dto.getMarks());
        existing.setActive(dto.getActive());

        Question updated = questionRepository.save(existing);
        return mapper.map(updated, QuestionDto.class);
    }

    @Override
    public String deleteQuestion(Long questionId) {
        Question existing = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id " + questionId));
        questionRepository.deleteById(questionId);
        return "Question is successfully deleted with id " + questionId;
    }
	
}
