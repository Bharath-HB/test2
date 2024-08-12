package com.example.Survey_Service.feign;

import com.example.Survey_Service.Client.Assessment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="assessmentservice",url="http://localhost:8083/assessment")
public interface AssessmentClient {
    @GetMapping("/getAllAssessment")
    public List<Assessment> getAllAssessment();


    @GetMapping("/assessments/{assessmentId}")
    Assessment findAssessmentById(@PathVariable("assessmentId") String assessmentId);

}
