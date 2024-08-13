package com.example.QuestionService.repo;

import com.example.QuestionService.model.Answers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepo extends JpaRepository<Answers,Long> {
}
