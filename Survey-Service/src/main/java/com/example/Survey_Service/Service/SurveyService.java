package com.example.Survey_Service.Service;

import com.example.Survey_Service.Model.Survey;
import com.example.Survey_Service.Model.SurveyStatus;
import com.example.Survey_Service.Repository.SurveyRepository;
import com.example.Survey_Service.dto.dtoToEntity;
import com.example.Survey_Service.feign.AssessmentClient;
import com.example.Survey_Service.Client.Assessment;
import com.example.Survey_Service.Client.FullResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService {

    private final SurveyRepository repo;

    private final AssessmentClient assessmentClient;

    public SurveyService(SurveyRepository repo, AssessmentClient assessmentClient) {
        this.repo = repo;
        this.assessmentClient = assessmentClient;
    }

    public Survey addSurvey(Survey survey) {
        try{
            survey.setStatus(SurveyStatus.SURVEY_REQUESTED);
            survey.setAssesmentId(null);  // set assessmentId to null initially
            return repo.save(survey);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to add survey",e);
        }

    }

    public List<Survey> getSurveyDetails() {
        try{
            return repo.findAll();
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to get survey details",e);
        }

    }

    public Survey getSurveyById(Long surveyid) {
        try{
            return repo.findById(surveyid).orElse(null);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to get survey by id",e);
        }

    }

    public Survey assignSurvey1(Long surveyId, String assessmentId){
        try{
            Survey survey = repo.findById(surveyId).orElse(null);
            Assessment assessment = assessmentClient.findAssessmentById(assessmentId);
            FullResponse res = new FullResponse();
            res.setSurveyid(surveyId);
            assert survey != null;
            res.setCname(survey.getCname());
            res.setCemail(survey.getCemail());
            res.setRequester(survey.getRequester());
            res.setAssessmentId(assessmentId);
            res.setDomain(assessment.getDomain());
            res.setSetname(assessment.getSetname());
            res.setStatus(SurveyStatus.SURVEY_COMPLETED);
            //res.setQuestionList(assessment.getQuestions());
            res.setCreatedby(assessment.getCreatedBy());
            Survey convertedSurvey = dtoToEntity.convertToEntity(res);
            return repo.save(convertedSurvey);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to assign survey",e);
        }

    }


//    public ResponseEntity<Survey> assignSurvey(Long survey, String setid) {
//        Optional<Survey> surveyOptional= repo.findById(survey);
//        if (surveyOptional.isPresent()) {
//           // surveyOptional.get().setAssesmentId(setid);
//            return ResponseEntity.ok(repo.save(surveyOptional.get()));
//        }
//        return ResponseEntity.noContent().build();
//    }

}
