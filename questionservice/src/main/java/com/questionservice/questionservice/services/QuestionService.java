package com.questionservice.questionservice.services;

import java.util.List;
import java.util.Optional;

import com.questionservice.questionservice.entity.Question;

public interface QuestionService {

    Question createQuestion(Question question);

    List<Question> getAllQuestionzes();

    Optional<Question> getQuestionById(Long id);

    Question updateQuestion(Long id, Question questionDetails);

    void deleteQuestion(Long id);

    List<Question> getQuestionByQuizId(Long quizId);
    
}
