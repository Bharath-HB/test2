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

    @GetMapping("/question/{qid}")
    public Question getQuestionById(@PathVariable Long qid);

    @GetMapping("/{setname}")
    public ResponseEntity<Assessment> getAssessmentBySetname(@PathVariable String setname,@RequestParam(value = "qid", required = false) Long qid);

}

