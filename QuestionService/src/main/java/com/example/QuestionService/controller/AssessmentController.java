package com.example.QuestionService.Controller;

import com.example.QuestionService.Dto.AssessmentDto;
import com.example.QuestionService.Dto.QuestionDto;
import com.example.QuestionService.Model.Assessment;
import com.example.QuestionService.Model.Question;
import com.example.QuestionService.Service.AssessmentService;
import com.example.QuestionService.Service.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing Assessments and related Questions.
 */
@RestController
@RequestMapping("/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;
    private final QuestionService questionService;

    public AssessmentController(AssessmentService assessmentService, QuestionService questionService) {
        this.assessmentService = assessmentService;
        this.questionService = questionService;
    }

    /**
     * Creates a new assessment.
     *
     * @param assessmentDto The details of the assessment to create, provided in the request body.
     * @return A ResponseEntity containing the created Assessment and HTTP status 201 Created.
     */
    @PostMapping
    public ResponseEntity<Assessment> createAssessment(@RequestBody AssessmentDto assessmentDto) {
        Assessment createdAssessment = assessmentService.createAssessment(assessmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssessment);
    }

    /**
     * Retrieves all assessments.
     *
     * @return A ResponseEntity containing a list of all Assessments and HTTP status 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Assessment>> getAssessments() {
        List<Assessment> assessments = assessmentService.getAllAssessments();
        return ResponseEntity.status(HttpStatus.OK).body(assessments);
    }

    /**
     * Updates a specific question within an assessment.
     *
     * @param setid The ID of the assessment set containing the question to update, provided as a path variable.
     * @param qid The ID of the question to update, provided as a path variable.
     * @param questionDto The updated question details, provided in the request body.
     * @return A ResponseEntity containing the updated Question and HTTP status 202 Accepted.
     */
    @PutMapping("/{setid}/{qid}")
    public ResponseEntity<Object> updateAssessmentbyqid(
            @PathVariable Long setid,
            @PathVariable Long qid,
            @RequestBody QuestionDto questionDto) {

        Question updatedQuestion = assessmentService.updateAssessmentbyqid(setid, qid, questionDto);
           return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedQuestion);


    }

    /**
     * Deletes a specific question from an assessment.
     *
     * @param setid The ID of the assessment set containing the question to delete, provided as a path variable.
     * @param qid The ID of the question to delete, provided as a path variable.
     * @return HTTP status 204 No Content to indicate successful deletion without a response body.
     */
    @DeleteMapping("/{setid}/{qid}")
    public ResponseEntity<String> deleteAssessment(
            @PathVariable Long setid,
            @PathVariable Long qid) {
           return ResponseEntity.status(HttpStatus.OK).body(assessmentService.deleteAssessmentByQidAndSetId(setid, qid));
    }

    @GetMapping("/question/{qid}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long qid) {
        return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestionById(qid));
    }

    @GetMapping("/{setname}")
    public ResponseEntity<AssessmentDto> getAssessmentBySetname(@PathVariable String setname,@RequestParam(required = false) Long qid){
        return ResponseEntity.status(HttpStatus.OK).body(assessmentService.getAssessmentBySetname(setname, qid));
    }

    @PutMapping("/{setname}")
    public ResponseEntity<Question> updateAssessmentBySetname(@PathVariable String setname,@RequestParam(required = false) Long qid,@RequestBody Question question){
        return ResponseEntity.status(HttpStatus.OK).body(assessmentService.updateAssessmentBySetname(setname, qid,question));
    }
}
