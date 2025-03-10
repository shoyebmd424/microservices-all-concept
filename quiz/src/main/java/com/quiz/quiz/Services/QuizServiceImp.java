package com.quiz.quiz.Services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.quiz.entity.Quizs;
import com.quiz.quiz.external.QuestionClient;
import com.quiz.quiz.repository.QuizRepository;

@Service
public class QuizServiceImp implements QuizService {
     
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionClient questionClient;

    @Override
    public Quizs createQuiz(Quizs quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quizs> getAllQuizszes() {
        List<Quizs> quizs= quizRepository.findAll();
        quizs.stream().map(quiz->{
            quiz.setQuestions(questionClient.getQuestionOfQuizes(quiz.getId()));
            return quiz;
        }).collect(Collectors.toList());

        return quizs;
    }

    @Override
    public Quizs getQuizById(Long id) {
        Quizs quiz= quizRepository.findById(id).get();
        quiz.setQuestions(questionClient.getQuestionOfQuizes(quiz.getId()));
        return quiz;
    }

    @Override
    public Quizs updateQuiz(Long id, Quizs quizDetails) {
        Optional<Quizs> existingQuiz = quizRepository.findById(id);
        if (existingQuiz.isPresent()) {
            Quizs quiz = existingQuiz.get();
            quiz.setTitle(quizDetails.getTitle());
            return quizRepository.save(quiz);
        } else {
            return null; 
        }
    }

    @Override
    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }



}
