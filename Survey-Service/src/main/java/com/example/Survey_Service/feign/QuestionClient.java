package com.example.Survey_Service.feign;

import com.example.Survey_Service.Client.Assessment;
import com.example.Survey_Service.Client.Question;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(name = "questionService", url = "http://localhost:9098/assessments", configuration = FeignConfiguration.class)

public interface QuestionClient {
    @GetMapping("/findAssessmentBySetId/{setname}")
    public ResponseEntity<Assessment> findAssessmentBySetname(@RequestParam String setname);

    @GetMapping("/{setid}")
    public ResponseEntity<List<Question>> getQuestions(@PathVariable Long setid);

    @GetMapping("/question/{qid}")
    public Question getQuestionById(@PathVariable Long qid);

}

