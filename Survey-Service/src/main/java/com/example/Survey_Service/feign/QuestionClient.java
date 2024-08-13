package com.example.Survey_Service.feign;

import com.example.Survey_Service.Client.Assessment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="QuestionService",url="http://localhost:8082/assessments")
public interface QuestionClient {
    @GetMapping("/findAssessmentBySetId/{setid}")
    public Assessment findAssessmentBySetId(@RequestParam Long setid);

    @GetMapping
    public ResponseEntity<List<Assessment>> getAssessments();
}
