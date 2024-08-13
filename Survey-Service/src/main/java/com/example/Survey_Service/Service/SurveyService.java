package com.example.Survey_Service.Service;

import com.example.Survey_Service.Model.Survey;
import com.example.Survey_Service.Model.SurveyStatus;
import com.example.Survey_Service.Repository.SurveyRepository;
import com.example.Survey_Service.dto.dtoToEntity;
import com.example.Survey_Service.Client.Assessment;
import com.example.Survey_Service.Client.FullResponse;
import com.example.Survey_Service.feign.QuestionClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService {

    private final SurveyRepository repo;

    private final QuestionClient questionClient;
    public SurveyService(SurveyRepository repo,QuestionClient questionClient) {
        this.repo = repo;
        this.questionClient = questionClient;
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

    public Survey assignSurvey(Long surveyId, Long assessmentId){
        try{
            Survey survey = repo.findById(surveyId).orElse(null);
            Assessment assessment = questionClient.findAssessmentBySetId(assessmentId);
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
            res.setCreatedby(assessment.getCreatedby());
            Survey convertedSurvey = dtoToEntity.convertToEntity(res);
            return repo.save(convertedSurvey);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to assign survey",e);
        }

    }

}
