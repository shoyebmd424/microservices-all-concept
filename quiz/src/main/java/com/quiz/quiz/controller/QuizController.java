package com.quiz.quiz.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.quiz.quiz.Services.QuizService;
import com.quiz.quiz.entity.Quizs;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    private static Logger logger=LoggerFactory.getLogger(QuizController.class);
    // Create a new quiz
    @PostMapping
    public ResponseEntity<Quizs> createQuiz(@RequestBody Quizs quiz) {
        Quizs createdQuiz = quizService.createQuiz(quiz);
        return new ResponseEntity<>(createdQuiz, HttpStatus.CREATED);
    }

    // Get all quizzes
    @GetMapping
    @CircuitBreaker(name = "quizQuestionBreaker",fallbackMethod = "quizQuestionFallBackAll")
    public ResponseEntity<List<Quizs>> getAllQuizszes() {
        List<Quizs> quizszes = quizService.getAllQuizszes();
        return new ResponseEntity<>(quizszes, HttpStatus.OK);
    }

    public ResponseEntity<List<Quizs>> quizQuestionFallBackAll() {
        logger.error("Fallback executed because the service is down: "); 
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    int retryCount=1;

    // Get a quiz by ID
    @GetMapping("/{id}")
    // @CircuitBreaker(name = "quizQuestionBreaker",fallbackMethod = "quizQuestionFallBack")
    // @Retry(name="quizQuestionBreaker",fallbackMethod = "quizQuestionFallBack")
    @RateLimiter(name="quizQuestionBreaker",fallbackMethod = "quizQuestionFallBack")
    public ResponseEntity<Quizs> getQuizById(@PathVariable Long id) {
        logger.info("Retry Count: {} ", retryCount);
        retryCount++; 
        Quizs quiz = quizService.getQuizById(id);
        return new ResponseEntity<>(quiz,HttpStatus.OK);
    }

    // fall back Method
    public ResponseEntity<Quizs> quizQuestionFallBack(Long id, Exception ex){
        logger.error("Fallback executed because the service is down: "); 
        return new ResponseEntity<>(new Quizs(), HttpStatus.OK); 
    }
    

    // Update an existing quiz
    @PutMapping("/{id}")
    public ResponseEntity<Quizs> updateQuiz(@PathVariable Long id, @RequestBody Quizs quizDetails) {
        Quizs updatedQuiz = quizService.updateQuiz(id, quizDetails);
        if (updatedQuiz != null) {
            return new ResponseEntity<>(updatedQuiz, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete a quiz by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
