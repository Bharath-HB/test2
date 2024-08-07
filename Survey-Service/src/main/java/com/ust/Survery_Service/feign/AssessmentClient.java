package com.ust.Survery_Service.feign;

import com.ust.Survery_Service.Client.Assessment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="AssessmentService",url="http://localhost:8082/assessments")
public interface AssessmentClient {
    @GetMapping("/getAllAssessment")
    public List<Assessment> getAllAssessment();


    @GetMapping("/assessments/{assessmentId}")
    Assessment findAssessmentById(@PathVariable("assessmentId") String assessmentId);

}
