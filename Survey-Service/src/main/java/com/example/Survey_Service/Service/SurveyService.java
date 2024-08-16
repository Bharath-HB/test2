package com.example.Survey_Service.Service;

import com.example.Survey_Service.Client.Question;
import com.example.Survey_Service.Model.Survey;
import com.example.Survey_Service.Model.SurveyStatus;
import com.example.Survey_Service.Repository.SurveyRepository;
import com.example.Survey_Service.dto.dtoToEntity;
import com.example.Survey_Service.Client.Assessment;
import com.example.Survey_Service.Client.FullResponse;
import com.example.Survey_Service.feign.QuestionClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public FullResponse getSurveyById(Long surveyid) {
        try{
            Optional<Survey> surveyOptional = repo.findById(surveyid);
            if(surveyOptional.isPresent()){
                Survey survey = surveyOptional.get();
                FullResponse response = new FullResponse();
                Assessment assessment = questionClient.findAssessmentBySetId(surveyOptional.get().getSetid());
                List<Question> questions = questionClient.getQuestions(surveyOptional.get().getSetid());
                response.setSurveyid(surveyOptional.get().getSurveyid());
                response.setRequester(surveyOptional.get().getRequester());
                response.setCname(surveyOptional.get().getCname());
                response.setCemail(surveyOptional.get().getCemail());
                response.setStatus(SurveyStatus.SURVEY_COMPLETED);
                response.setSetid(surveyOptional.get().getSetid());
                response.setDomain(surveyOptional.get().getDomain());
                response.setQuestions(questions);
                response.setCreatedby(assessment.getCreatedby());
                return response;
            }else{
                throw new RuntimeException("No survey found with given id");
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to get survey by id",e);
        }
    }

//    public Survey assignSurvey(Long surveyId, Long assessmentId){
//        try{
//            Survey survey = repo.findById(surveyId).orElse(null);
//            Assessment assessment = questionClient.findAssessmentBySetId(assessmentId);
//            FullResponse res = new FullResponse();
//            res.setSurveyid(surveyId);
//            assert survey != null;
//            res.setCname(survey.getCname());
//            res.setCemail(survey.getCemail());
//            res.setRequester(survey.getRequester());
//            res.setSetid(assessmentId);
//            res.setDomain(assessment.getDomain());
//            res.setStatus(SurveyStatus.SURVEY_COMPLETED);
//            res.setQuestions(assessment.getQuestions());
//            res.setCreatedby(assessment.getCreatedby());
//            Survey convertedSurvey = dtoToEntity.convertToEntity(res);
//            return repo.save(convertedSurvey);
//        }
//        catch (Exception e) {
//            throw new RuntimeException("Failed to assign survey",e);
//        }
//
//    }

}
