package com.example.QuestionService.Repo;

import com.example.QuestionService.Model.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssessmentRepo extends JpaRepository<Assessment,Long> {
    Optional<Assessment> findBySetname(String setname);

}
