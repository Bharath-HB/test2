package com.example.Survey_Service.Controller;

import com.example.Survey_Service.Client.FullResponse;
import com.example.Survey_Service.Exception.ResourceNotFoundException;
import com.example.Survey_Service.Exception.SurveyNotFoundException;
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
        return new ResponseEntity<Survey>(surveyService.addSurvey(survey),HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Survey>> getSurveyDetails(){
        return new ResponseEntity<List<Survey>>(surveyService.getSurveyDetails(), HttpStatus.OK);
    }

    @GetMapping("/{surveyid}")
    public ResponseEntity<FullResponse> getSurveyById(@PathVariable Long surveyid) {
        return new ResponseEntity<FullResponse>(surveyService.getSurveyById(surveyid), HttpStatus.OK);
    }

    @PutMapping("/{surveyid}/addQuestions")
    public ResponseEntity<FullResponse> addListQuestions(@PathVariable Long surveyid, @RequestBody List<Long> qids) {
        return new ResponseEntity<FullResponse>(surveyService.addListQuestions(surveyid, qids), HttpStatus.OK);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleAssessmentNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.OK).body(ex.getMessage());
    }

}
