package com.ust.Survery_Service.Controller;

import com.ust.Survery_Service.Client.Assessment;
import com.ust.Survery_Service.Client.FullResponse;
import com.ust.Survery_Service.Model.Survey;
import com.ust.Survery_Service.Service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/survey")
public class SurveyController {
    @Autowired
    private SurveyService surveyService;

    @PostMapping()
    public ResponseEntity<Survey> addSurvey(@RequestBody Survey survey ) {
        return ResponseEntity.ok(surveyService.addSurvey(survey));
    }

    @GetMapping()
    public ResponseEntity<List<Survey>> getSurveyDetails(){
        return ResponseEntity.ok(surveyService.getSurveyDetails());
    }

    @GetMapping("/{surveyid}")
    public ResponseEntity<List<Assessment>> getSurveyById(@PathVariable Long surveyid) {
        return ResponseEntity.ok(surveyService.getSurveyById(surveyid));
    }

    @PutMapping("/assign/{surveyid}")
    public FullResponse assignSurvey(@PathVariable Long survey, @RequestParam  String setid){
        return surveyService.assignSurvey1(survey, setid);
    }
}
