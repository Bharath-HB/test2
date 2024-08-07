package com.ust.Survery_Service.Service;

import com.ust.Survery_Service.Client.Assessment;
import com.ust.Survery_Service.Client.FullResponse;
import com.ust.Survery_Service.Model.Survey;
import com.ust.Survery_Service.Model.SurveyStatus;
import com.ust.Survery_Service.Repository.SurveyRepository;
import com.ust.Survery_Service.dto.dtoToEntity;
import com.ust.Survery_Service.feign.AssessmentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {
    @Autowired
    private SurveyRepository repo;

    @Autowired
    private AssessmentClient assessmentClient;

    public Survey addSurvey(Survey survey) {
        survey.setStatus(SurveyStatus.SURVEY_REQUESTED);
        survey.setAssesmentId(null);  // set assessmentId to null initially
        return repo.save(survey);
    }

    public List<Survey> getSurveyDetails() {
        return repo.findAll();
    }

    public List<Assessment> getSurveyById(Long surveyid) {
        //return repo.findById(surveyid).orElse(null);
        Optional<Survey> surveyOptional= repo.findById(surveyid);
        return surveyOptional.map(survey -> assessmentClient.getAllAssessment()
                .stream()
                .filter(assessment -> false)
                .toList()).orElse(null);
    }

    public Survey assignSurvey1(Long surveyId, String assessmentId){
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
        res.setStatus(SurveyStatus.SURVEY_COMPLETED);
        res.setQuestionList(assessment.getQuestions());
        res.setCreatedby(assessment.getCreatedBy());
        Survey convertedSurvey = dtoToEntity.convertToEntity(res);
        return repo.save(convertedSurvey);
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
