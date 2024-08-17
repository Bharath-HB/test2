package com.example.QuestionService.controller;

import com.example.QuestionService.dto.AssessmentDto;
import com.example.QuestionService.dto.QuestionDto;
import com.example.QuestionService.model.Assessment;
import com.example.QuestionService.model.Question;
import com.example.QuestionService.service.AssessmentService;
import com.example.QuestionService.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.QuestionService.dto.EntityToDto.convertToEntity;

@RestController
@RequestMapping("/assessments")
public class AssessmentController {




    private final AssessmentService assessmentService;

    private final QuestionService questionService;

    public AssessmentController(AssessmentService assessmentService, QuestionService questionService) {
        this.assessmentService = assessmentService;
        this.questionService = questionService;
    }




    @PostMapping
    public ResponseEntity<Assessment> createAssessment(@RequestBody AssessmentDto assessment) {

       return ResponseEntity.status(HttpStatus.CREATED).body(assessmentService.createAssessment(assessment));

    }
    @GetMapping
    public ResponseEntity<List<Assessment>> getAssessments() {


        return ResponseEntity.status(HttpStatus.OK).body(assessmentService.getAllAssessments());

    }

    @GetMapping("/{setid}")
    public ResponseEntity<List<Question>> getQuestions(@PathVariable Long setid) {
        return ResponseEntity.status(HttpStatus.OK).body(questionService.getAllQuestions(setid));
    }

    @PutMapping("/{setid}/{qid}")
    public ResponseEntity<Question> updateAssessmentbyqid(@PathVariable Long setid,@PathVariable Long qid, @RequestBody QuestionDto question) {

       return ResponseEntity.status(HttpStatus.ACCEPTED).body(assessmentService.updateAssessmentbyqid(setid,qid, question));
    }

    @DeleteMapping("/{setid}/{qid}")
    public void deleteAssessment(@PathVariable Long setid, @PathVariable Long qid) {
        assessmentService.deleteAssessmentByQidAndSetId(setid, qid);
    }



    @GetMapping("/findAssessmentBySetId/{setid}")
    public Assessment findAssessmentBySetId(@RequestParam Long setid) {
        return (Assessment) assessmentService.findAssessmentBySetId(setid);
    }

    @GetMapping("/getQuestionById/{qid}")
    public Question getQuestionById(@PathVariable Long qid) {
        return questionService.getQuestionById(qid);
    }

}
