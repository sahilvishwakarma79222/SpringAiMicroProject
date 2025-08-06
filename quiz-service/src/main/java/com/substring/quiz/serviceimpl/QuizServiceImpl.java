package com.substring.quiz.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.substring.quiz.collection.Quiz;
import com.substring.quiz.dto.CategoryDto;
import com.substring.quiz.dto.QuizDto;
import com.substring.quiz.repository.QuizRepository;
import com.substring.quiz.service.CategoryFeignService;
import com.substring.quiz.service.CategorySerrvice;
import com.substring.quiz.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService{

	private org.slf4j.Logger logger=LoggerFactory.getLogger(QuizServiceImpl.class);
	private final QuizRepository quizRepository;
	private final ModelMapper mapper;
	private final RestTemplate restTemplate;
	private final CategorySerrvice categoryWebClientSerrvice;
	private final CategoryFeignService categoryFeignService;
//	private final WebClient webClient;  //coz humne alg se category serve bana liya hai 
	private final WebClient.Builder webClientBuilder;
	
	public QuizServiceImpl(QuizRepository quizRepository,ModelMapper mapper,RestTemplate restTemplate
//			,WebClient webClient
			,CategorySerrvice categorySerrvice
			,CategoryFeignService categoryFeignService
			,WebClient.Builder webClientBuilder) {
		this.mapper=mapper;
		this.quizRepository=quizRepository;
		this.restTemplate=restTemplate;
		this.categoryWebClientSerrvice=categorySerrvice;
		this.categoryFeignService=categoryFeignService;
		this.webClientBuilder=webClientBuilder;
		
//		this.webClient=webClientBuilder.baseUrl("http://CATEGORY-SERVICE").build();
	}
	
	@Override 
	public QuizDto saveQuiz(QuizDto dto) {
	
		Quiz entity = mapper.map(dto, Quiz.class);
		entity.setId(UUID.randomUUID().toString());
		
//		String url="http://CATEGORY-SERVICE:9091/api/v1/category/catId/"+entity.getCategoryId();
//		CategoryDto category = restTemplate.getForObject(url, CategoryDto.class);
//		logger.info("category exist  "+category.getTitle());

        System.out.println("category id is"+dto.getCategoryId());
        CategoryDto categoryDto = categoryFeignService.findByCategoryId(dto.getCategoryId());


        Quiz entity1 = quizRepository.save(entity);
        QuizDto responseDto = mapper.map(entity1, QuizDto.class);
        responseDto.setCategorydto(categoryDto);
        return responseDto;
	}

	@Override
	public List<QuizDto> allQuiz() {
		
		List<Quiz> all = quizRepository.findAll();
		List<QuizDto> quizDtos=all.stream().map(quiz->{
			String categoryId = quiz.getCategoryId();
			QuizDto quizDto=mapper.map(quiz, QuizDto.class);
			//call to quiz service using webclient
			CategoryDto categoryDto = this.categoryWebClientSerrvice.findById(categoryId);
			quizDto.setCategorydto(categoryDto);
			return quizDto;
		}).toList();
		
		return quizDtos;
		
	}
	
	@Override
	public List<QuizDto> getByCategoryId(String categoryId){
		
		List<Quiz> quizesByCategoryId = quizRepository.findByCategoryId(categoryId);

		List<QuizDto> list = quizesByCategoryId.stream().map(q->
		{
			 QuizDto dto = mapper.map(q, QuizDto.class);
			 CategoryDto byCategoryId = categoryFeignService.findByCategoryId(dto.getCategoryId());
			 dto.setCategorydto(byCategoryId);
			 return dto;
		}
		).toList();
		return list;
	}

	@Override
	public QuizDto updateQuiz(String quizId, QuizDto dto) {
		quizRepository.findById(quizId).orElseThrow(()-> new RuntimeException("quiz not found with this id"));
		Quiz entity = mapper.map(dto, Quiz.class);
		entity.setId(quizId);
		

		String url="lb://localhost:9091/api/v1/category/catId/"+entity.getCategoryId();
		CategoryDto category = restTemplate.getForObject(url, CategoryDto.class); 
		logger.info("category exist  "+category.getTitle());
		
		Quiz save = quizRepository.save(entity);
		QuizDto responseDto = mapper.map(save, QuizDto.class);
		return responseDto;
	
	}

	@Override
	public String deleteQuiz(String quizId) {
		 Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);
	        if (optionalQuiz.isEmpty()) {
	            throw new RuntimeException("Quiz not found with id: " + quizId);
	        }
	        quizRepository.deleteById(quizId);
	        return "Quiz deleted with id: " + quizId;
	}

//    ye webclient ke saath hai niche feign se kiya hu
	@Override
	public QuizDto getQuizById(String quizId) {
		Optional<Quiz> quiz = quizRepository.findById(quizId);
			String categoryId = quiz.get().getCategoryId();
			QuizDto dto = mapper.map(quiz.get(), QuizDto.class);
			String url="lb://CATEGORY-SERVICE/api/v1/category/catId/"+quiz.get().getCategoryId();
			logger.info(url);
			CategoryDto category = restTemplate.getForObject(url, CategoryDto.class);
			dto.setCategorydto(category);

        return dto;
	}

    // isme category ko feign client se call kiya hu
//    @Override
//    public QuizDto getQuizById(String quizId) {
//        Optional<Quiz> quiz = quizRepository.findById(quizId);
//        String categoryId = quiz.get().getCategoryId();
//        QuizDto dto = mapper.map(quiz.get(), QuizDto.class);
//
//        CategoryDto catDto = categoryFeignService.findByCategoryId(quiz.get().getCategoryId());
//        dto.setCategorydto(catDto);
//
//        return dto;
//    }

    // agr category nhi mila to exception milega jo ki multiple ho skte


}
