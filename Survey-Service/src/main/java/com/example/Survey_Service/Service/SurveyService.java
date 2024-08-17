package com.example.Survey_Service.Service;

import com.example.Survey_Service.Client.Question;
import com.example.Survey_Service.Exception.ExternalServiceException;
import com.example.Survey_Service.Exception.SurveyNotFoundException;
import com.example.Survey_Service.Model.Survey;
import com.example.Survey_Service.Model.SurveyStatus;
import com.example.Survey_Service.Repository.SurveyRepository;
import com.example.Survey_Service.Client.Assessment;
import com.example.Survey_Service.Client.FullResponse;
import com.example.Survey_Service.feign.QuestionClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        try {
            Optional<Survey> surveyOptional = repo.findById(surveyid);
            if (surveyOptional.isEmpty()) {
                throw new SurveyNotFoundException("No survey found with id: " + surveyid);
            }

            Survey survey = surveyOptional.get();
            FullResponse response = new FullResponse();

            try {
                Assessment assessment = questionClient.findAssessmentBySetId(survey.getSetid());
                List<Question> questions = questionClient.getQuestions(survey.getSetid());

                response.setSurveyid(survey.getSurveyid());
                response.setRequester(survey.getRequester());
                response.setCname(survey.getCname());
                response.setCemail(survey.getCemail());
                response.setStatus(SurveyStatus.SURVEY_COMPLETED);
                response.setSetid(survey.getSetid());
                response.setDomain(survey.getDomain());
                response.setQuestions(questions);
                response.setCreatedby(assessment.getCreatedby());
            } catch (Exception e) {
                throw new ExternalServiceException("Failed to retrieve assessment or questions", e);
            }

            return response;
        } catch (SurveyNotFoundException | ExternalServiceException e) {
            throw e; // Re-throwing known exceptions to be handled by a global exception handler
        } catch (Exception e) {
            // Log the exception
            // Example: logger.error("Unexpected error occurred while getting survey by ID", e);
            throw new RuntimeException("Failed to get survey", e);
        }
    }


    public FullResponse addListQuestions(Long surveyid, List<Long> qids) {
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
                response.setCreatedby(assessment.getCreatedby());
                List<Question> mergedQuestions = new ArrayList<>(questions);
                for (Long qid : qids) {
                    Question newQuestion = questionClient.getQuestionById(qid);
                    boolean isDuplicate = mergedQuestions.stream()
                            .anyMatch(existingQuestion -> existingQuestion.getQid().equals(newQuestion.getQid()));
                    if (!isDuplicate) {
                        mergedQuestions.add(newQuestion);
                    }
                }
                response.setQuestions(mergedQuestions);
                return response;
            }else{
                throw new RuntimeException("No survey found with given id");
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to add question ",e);
        }
    }
}
