package com.questionservice.questionservice.config;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.questionservice.questionservice.entity.Question;
import com.questionservice.questionservice.repository.QeustionRepository;


@Configuration
public class QuizDataInitializer {

    @Autowired
    private QeustionRepository qeustionRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            if (qeustionRepository.count() == 0) {
                List<Question> quizzes = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    Question question = new Question();
                    question.setQuestion("Questions Tomcat started " +i);
                    question.setQuizId((long)i%3);
                    quizzes.add(question);
                }
                qeustionRepository.saveAll(quizzes);
            }
        };
    }

}
