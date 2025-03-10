package com.quiz.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.quiz.entity.Quizs;

@Repository
public interface QuizRepository extends JpaRepository<Quizs,Long> {
    
}
