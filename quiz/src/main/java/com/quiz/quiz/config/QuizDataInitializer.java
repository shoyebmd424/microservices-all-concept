package com.quiz.quiz.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.quiz.quiz.entity.Quizs;
import com.quiz.quiz.repository.QuizRepository;

@Configuration
public class QuizDataInitializer {

    @Autowired
    private QuizRepository quizRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            if (quizRepository.count() == 0) {
                List<Quizs> quizzes = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    Quizs quiz = new Quizs();
                    quiz.setTitle("Quiz " + (100 + Math.random()));
                    quizzes.add(quiz);
                }
                quizRepository.saveAll(quizzes);
            }
        };
    }

}
