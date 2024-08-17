package com.example.Survey_Service.Controller;

import com.example.Survey_Service.Client.FullResponse;
import com.example.Survey_Service.Model.Survey;
import com.example.Survey_Service.Service.SurveyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/survey")
public class SurveyController {
    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }


    @PostMapping()
    public ResponseEntity<Survey> addSurvey(@RequestBody Survey survey ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(surveyService.addSurvey(survey));//ok(surveyService.addSurvey(survey));
    }

    @GetMapping()
    public ResponseEntity<List<Survey>> getSurveyDetails(){
        return ResponseEntity.ok(surveyService.getSurveyDetails());
    }

    @GetMapping("/{surveyid}")
    public ResponseEntity<FullResponse> getSurveyById(@PathVariable Long surveyid) {
        return ResponseEntity.ok(surveyService.getSurveyById(surveyid));
    }

    @PutMapping("/{surveyid}/addQuestions")
    public ResponseEntity<FullResponse> addListQuestions(@PathVariable Long surveyid, @RequestBody List<Long> qids) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(surveyService.addListQuestions(surveyid, qids));
    }

}
