package com.example.Survey_Service.Service;

import com.example.Survey_Service.Client.Question;
import com.example.Survey_Service.Exception.DatabaseException;
import com.example.Survey_Service.Exception.ExternalServiceException;
import com.example.Survey_Service.Exception.ResourceNotFoundException;
import com.example.Survey_Service.Exception.SurveyNotFoundException;
import com.example.Survey_Service.Model.Survey;
import com.example.Survey_Service.Model.SurveyStatus;
import com.example.Survey_Service.Repository.SurveyRepository;
import com.example.Survey_Service.Client.Assessment;
import com.example.Survey_Service.Client.FullResponse;
import com.example.Survey_Service.dto.dtoToEntity;
import com.example.Survey_Service.feign.QuestionClient;
import com.fasterxml.jackson.core.type.TypeReference;
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

    private dtoToEntity dto;

    private final QuestionClient questionClient;

    public SurveyService(SurveyRepository repo,QuestionClient questionClient,dtoToEntity dto) {
        this.repo = repo;
        this.questionClient = questionClient;
        this.dto = dto;
    }

    public Survey addSurvey(Survey survey) {
        try {
            survey.setStatus(SurveyStatus.SURVEY_REQUESTED);
            ResponseEntity<Assessment> assessmentResponse = questionClient.findAssessmentBySetName(survey.getSetname());
            if (assessmentResponse.getStatusCode() == HttpStatus.OK && assessmentResponse.getBody() != null) {
                return repo.save(survey);
            } else {
                throw new ResourceNotFoundException("Assessment not found for setname: " + survey.getSetname());
            }
        } catch (ResourceNotFoundException e) {
            throw e; // Re-throwing the custom exception for higher-level handling
        } catch (Exception e) {
            throw new RuntimeException("Failed to add survey", e);
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
            // Check if survey exists
            Optional<Survey> surveyOptional = repo.findById(surveyid);
            if (surveyOptional.isEmpty()) {
                throw new SurveyNotFoundException("No survey found with id: " + surveyid);
            }
            Survey survey = surveyOptional.get();
            FullResponse response = new FullResponse();

            // Fetch assessment
            Assessment assessment;
            try {
                assessment = questionClient.findAssessmentBySetName(survey.getSetname()).getBody();
            } catch (ExternalServiceException e) {
                throw new ExternalServiceException("Failed to retrieve assessment for setid: " + survey.getSetid(), e);
            }

            // Fetch questions
            List<Question> questions;
            try {
                questions = questionClient.getQuestions(survey.getSetname()).getBody();
            } catch (ExternalServiceException e) {
                throw new ExternalServiceException("Failed to retrieve questions for setid: " + survey.getSetid(), e);
            }

            List<Long> questionsIds = new ArrayList<>();
            response.setSurveyid(survey.getSurveyid());
            response.setRequester(survey.getRequester());
            response.setCname(survey.getCname());
            response.setCemail(survey.getCemail());
            response.setStatus(SurveyStatus.SURVEY_COMPLETED);
            response.setSetid(survey.getSetid());
            response.setDomain(survey.getDomain());
            response.setQuestions(questions);

            // Collect question IDs
            for (Question question : questions) {
                questionsIds.add(Long.valueOf(question.getQid()));
            }
            response.setQuestionIds(questionsIds);
            response.setCreatedby(assessment.getCreatedby());

            // Convert and save the updated survey
            Survey surveyToSave = dtoToEntity.convertToEntity(response);
            try {
                repo.save(surveyToSave);
            } catch (Exception e) {
                throw new DatabaseException("Failed to save updated survey with id: " + surveyid, e);
            }

            return response;

        } catch (SurveyNotFoundException | ExternalServiceException | DatabaseException e) {
            // Re-throw known exceptions to be handled by a global exception handler
            throw e;
        } catch (Exception e) {
            // Handle unexpected exceptions
            // Example: logger.error("Unexpected error occurred while retrieving survey", e);
            throw new RuntimeException("Failed to get survey with id: " + surveyid, e);
        }
    }



    public FullResponse addListQuestions(Long surveyid, List<Long> qids) {
        try {
            Optional<Survey> surveyOptional = repo.findById(surveyid);
            if (surveyOptional.isEmpty()) {
                throw new SurveyNotFoundException("No survey found with id: " + surveyid);
            }

            Survey survey = surveyOptional.get();
            FullResponse response = new FullResponse();

            // Fetch assessment and questions
            Assessment assessment;
            try {
                assessment = questionClient.findAssessmentBySetId(survey.getSetname()).getBody();
            } catch (ExternalServiceException e) {
                throw new ExternalServiceException("Failed to retrieve assessment for setid: " + survey.getSetid(), e);
            }

            List<Question> questions;
            try {
                questions = questionClient.getQuestions(survey.getSetname()).getBody();
            } catch (ExternalServiceException e) {
                throw new ExternalServiceException("Failed to retrieve questions for setid: " + survey.getSetid(), e);
            }

            List<Long> questionsIds = new ArrayList<>();
            response.setSurveyid(survey.getSurveyid());
            response.setRequester(survey.getRequester());
            response.setCname(survey.getCname());
            response.setCemail(survey.getCemail());
            response.setStatus(SurveyStatus.SURVEY_COMPLETED);
            response.setSetid(survey.getSetid());
            response.setDomain(survey.getDomain());
            response.setCreatedby(assessment.getCreatedby());

            List<Question> mergedQuestions = new ArrayList<>(questions);
            for (Long qid : qids) {
                Question newQuestion;
                try {
                    newQuestion = questionClient.getQuestionById(qid);
                } catch (ExternalServiceException e) {
                    throw new ExternalServiceException("Failed to retrieve question with id: " + qid, e);
                }

                newQuestion.setSetid(survey.getSetname());
                boolean isDuplicate = mergedQuestions.stream()
                        .anyMatch(existingQuestion -> existingQuestion.getQid().equals(newQuestion.getQid()));
                if (!isDuplicate) {
                    mergedQuestions.add(newQuestion);
                }
            }

            response.setQuestions(mergedQuestions);
            for (Question question : mergedQuestions) {
                questionsIds.add(Long.valueOf(question.getQid()));
            }
            response.setQuestionIds(questionsIds);

            // Convert and save the updated survey
            Survey surveyToSave = dtoToEntity.convertToEntity(response);
            try {
                repo.save(surveyToSave);
            } catch (Exception e) {
                throw new DatabaseException("Failed to save updated survey with id: " + surveyid, e);
            }

            return response;
        } catch (SurveyNotFoundException | ExternalServiceException | DatabaseException e) {
            throw e; // Re-throw known exceptions to be handled by a global exception handler
        } catch (Exception e) {
            // Log and wrap any unexpected exception
            // Example: logger.error("Unexpected error occurred while adding list of questions", e);
            throw new RuntimeException("Failed to add questions to survey", e);
        }
    }

}

