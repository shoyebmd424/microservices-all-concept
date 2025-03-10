package com.questionservice.questionservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.questionservice.questionservice.entity.Question;
import com.questionservice.questionservice.repository.QeustionRepository;

@Service
public class QuestionServiceIml implements QuestionService {

    @Autowired
    private QeustionRepository qeustionRepository;

    @Override
    public Question createQuestion(Question question) {
       return qeustionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestionzes() {
     return qeustionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
       return qeustionRepository.findById(id);
    }

    @Override
    public Question updateQuestion(Long id, Question questionDetails) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateQuestion'");
    }

    @Override
    public void deleteQuestion(Long id) {
      qeustionRepository.deleteById(id);
    }

    @Override
    public List<Question> getQuestionByQuizId(Long quizId) {
      return qeustionRepository.findByQuizId(quizId);
    }
    
}
