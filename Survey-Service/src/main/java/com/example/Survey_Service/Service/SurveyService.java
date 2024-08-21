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

    private final QuestionClient questionClient;

    public SurveyService(SurveyRepository repo,QuestionClient questionClient) {
        this.repo = repo;
        this.questionClient = questionClient;
    }

    public Survey addSurvey(Survey survey) {
        try {
            survey.setStatus(SurveyStatus.SURVEY_REQUESTED);
            ResponseEntity<Assessment> assessmentResponse = questionClient.getAssessmentBySetname(survey.getSetname(),null);
            List<Long> questionsIds = survey.getQuestionIds(); // Ensure this is a List<Long>
            if (assessmentResponse.getStatusCode() == HttpStatus.OK && assessmentResponse.getBody() != null) {
                Long setid = assessmentResponse.getBody().getSetid();
                if (questionsIds == null) {
                    try {
                        List<Question> questions = assessmentResponse.getBody().getQuestions();
                        questionsIds = questions.stream()
                                .map(question -> Long.parseLong(question.getQid())) // Ensure Qid is parsed to Long if needed
                                .collect(Collectors.toList());
                    } catch (ExternalServiceException e) {
                        throw new ExternalServiceException("Failed to retrieve questions for setname: " + survey.getSetname(), e);
                    }
                    survey.setQuestionIds(questionsIds);
                } else {
                    try{
                        List<Long> questionIds = new ArrayList<Long>();
                        for(Long qids : questionsIds) {
                            Assessment assessment1= questionClient.getAssessmentBySetname(survey.getSetname(), qids).getBody();
                            if (assessment1 == null) {
                                throw new DatabaseException("Question not found for id: " + qids);
                            }
                            questionIds.add(qids);
                        }
                        survey.setQuestionIds(questionIds);
                    }catch (Exception e) {
                        throw new ResourceNotFoundException("No question exist for the given set name : ");
                    }
                }
                return repo.save(survey);
            } else {
                throw new ResourceNotFoundException("Assessment not found for setname: " + survey.getSetname());
            }
        } catch (ResourceNotFoundException e) {
            throw e;
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
        Optional<Survey> surveyOptional = repo.findById(surveyid);
        if (surveyOptional.isEmpty()) {
            throw new SurveyNotFoundException("No survey found with id: " + surveyid);
        }
        Survey survey = surveyOptional.get();
        FullResponse response = new FullResponse();
        Assessment assessment;
        try {
            assessment = questionClient.getAssessmentBySetname(survey.getSetname(), null).getBody();
            if (assessment == null) {
                throw new ExternalServiceException("Failed to retrieve assessment for setname: " + survey.getSetname());
            }
        } catch (ExternalServiceException e) {
            throw new ExternalServiceException("Failed to retrieve assessment for setname: " + survey.getSetname(), e);
        }
        List<Question> questions = new ArrayList<>();
        try {
            Long setid = assessment.getSetid();
            for (Long qid : survey.getQuestionIds()) {
                // Fetch question by ID
                Question question = questionClient.getQuestionById(qid);
                if (question == null) {
                    throw new ExternalServiceException("Failed to retrieve question with id: " + qid);
                }
                question.setSetid(setid);
                questions.add(question);
            }
        } catch (ExternalServiceException e) {
            throw new ExternalServiceException("Failed to retrieve questions for setname: " + survey.getSetname(), e);
        }
        List<Long> questionsIds = new ArrayList<>();
        response.setSurveyid(survey.getSurveyid());
        response.setRequester(survey.getRequester());
        response.setCname(survey.getCname());
        response.setCemail(survey.getCemail());
        response.setStatus(SurveyStatus.SURVEY_COMPLETED);
        response.setDomain(survey.getDomain());
        response.setQuestions(questions);
        if (survey.getQuestionIds().isEmpty()) {
            for (Question question : questions) {
                questionsIds.add(Long.valueOf(question.getQid()));
            }
            response.setQuestionIds(questionsIds);
        } else {
            response.setQuestionIds(survey.getQuestionIds());
        }
        response.setCreatedby(assessment.getCreatedby());
        response.setSetname(survey.getSetname());
        Survey surveyToSave = dtoToEntity.convertToEntity(response);
        try {
            repo.save(surveyToSave);
        } catch (Exception e) {
            throw new DatabaseException("Failed to save updated survey with id: " + surveyid, e);
        }
        return response;
    }

    public FullResponse addListQuestions(Long surveyid, List<Long> qids) {
        try {
            Optional<Survey> surveyOptional = repo.findById(surveyid);
            if (surveyOptional.isEmpty()) {
                throw new SurveyNotFoundException("No survey found with id: " + surveyid);
            }
            Survey survey = surveyOptional.get();
            FullResponse response = new FullResponse();
            Assessment assessment;
            try {
                assessment= questionClient.getAssessmentBySetname(survey.getSetname(),null).getBody();
                if (assessment == null) {
                    throw new ExternalServiceException("Assessment response body is null for setname: " + survey.getSetname());
                }
            } catch (ExternalServiceException e) {
                throw new ExternalServiceException("Failed to retrieve assessment for setname: " + survey.getSetname(), e);
            }
            List<Question> questionList = new ArrayList<>();
            try {
                for(Long qid : survey.getQuestionIds()) {
                    Question questions = questionClient.getQuestionById(qid);
                    questions.setSetid(assessment.getSetid());
                    questionList.add(questions);
                }
            } catch (ExternalServiceException e) {
                throw new ExternalServiceException("Failed to retrieve questions for setname: " + survey.getSetname(), e);
            }
            List<Long> questionsIds = new ArrayList<>();
            response.setSurveyid(survey.getSurveyid());
            response.setRequester(survey.getRequester());
            response.setCname(survey.getCname());
            response.setCemail(survey.getCemail());
            response.setStatus(SurveyStatus.SURVEY_COMPLETED);
            response.setSetname(survey.getSetname());
            response.setDomain(survey.getDomain());
            response.setCreatedby(assessment.getCreatedby());
            List<Question> mergedQuestions = new ArrayList<>(questionList);
            for (Long qid : qids) {
                Question newQuestion;
                try {
                    newQuestion = questionClient.getQuestionById(qid);
                } catch (ExternalServiceException e) {
                    throw new ExternalServiceException("Failed to retrieve question with id: " + qid, e);
                }
                newQuestion.setSetid(assessment.getSetid());
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
            throw new RuntimeException("Failed to add questions to survey", e);
        }
    }

}

