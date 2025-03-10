package com.questionservice.questionservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.questionservice.questionservice.entity.Question;

public interface QeustionRepository extends JpaRepository<Question,Long> {
    List<Question> findByQuizId(Long quizId);
}
