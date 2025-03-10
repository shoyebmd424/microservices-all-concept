package com.questionservice.questionservice.controller;

import java.util.List;
import java.util.Optional;

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

import com.questionservice.questionservice.entity.Question;
import com.questionservice.questionservice.services.QuestionService;


@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // Create a new quiz
    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question Question) {
        Question createdQuestion = questionService.createQuestion(Question);
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }

    // Get all Questionzes
    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestionzes() {
        List<Question> Questionzes = questionService.getAllQuestionzes();
        return new ResponseEntity<>(Questionzes, HttpStatus.OK);
    }

    // Get a Question by ID
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        Optional<Question> Question = questionService.getQuestionById(id);
        return Question.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Update an existing Question
    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question QuestionDetails) {
        Question updatedQuestion = questionService.updateQuestion(id, QuestionDetails);
        if (updatedQuestion != null) {
            return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete a Question by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<Question>> getQuestionByQuizId(@PathVariable Long quizId){
        List<Question> Questionzes = questionService.getQuestionByQuizId(quizId);
        return new ResponseEntity<>(Questionzes, HttpStatus.OK);
    }
}
