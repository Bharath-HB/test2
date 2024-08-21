package com.example.QuestionService.Service;

import com.example.QuestionService.Exception.QuestionidNotFoundException;
import com.example.QuestionService.Exception.SetidNotFoundException;
import com.example.QuestionService.Model.Question;
import com.example.QuestionService.Repo.QuestionRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepo questionRepo;

    public QuestionService(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    public Question getQuestionById(Long qid) {
        return questionRepo.findById(qid).orElseThrow(() -> new QuestionidNotFoundException("Question not found"));
    }

}
