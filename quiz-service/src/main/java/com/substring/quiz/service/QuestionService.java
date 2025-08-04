package com.substring.quiz.service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="questions-service",url="http://localhost:9093")
public interface QuestionService {

}
