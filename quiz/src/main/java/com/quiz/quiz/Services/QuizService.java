package com.quiz.quiz.Services;

import java.util.List;

import com.quiz.quiz.entity.Quizs;

public interface QuizService {

    Quizs createQuiz(Quizs quiz);

    List<Quizs> getAllQuizszes();

    Quizs getQuizById(Long id);

    Quizs updateQuiz(Long id, Quizs quizDetails);

    void deleteQuiz(Long id);

    
} 