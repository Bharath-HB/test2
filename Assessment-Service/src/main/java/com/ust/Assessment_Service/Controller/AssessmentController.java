package com.ust.Assessment_Service.Controller;

import com.ust.Assessment_Service.Model.Assessment;
import com.ust.Assessment_Service.Repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assessment")
public class AssessmentController {
    @Autowired
    private AssessmentRepository repo;

    @PostMapping
    public Assessment createAssessment(@RequestBody Assessment assessment) {

        return repo.save(assessment);
    }

    @GetMapping("/getAllAssessment")
    public List<Assessment> getAllAssessment(){
        return repo.findAll();
    }

    @GetMapping("/assessments/{assessmentId}")
    public Assessment findAssessmentById(@PathVariable("assessmentId") String assessmentId){
        return repo.findById(assessmentId).orElse(null);
    }
}
