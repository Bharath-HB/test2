package com.example.QuestionService.Service;

import com.example.QuestionService.Dto.AssessmentDto;
import com.example.QuestionService.Dto.EntityToDto;
import com.example.QuestionService.Dto.QuestionDto;
import com.example.QuestionService.Exception.AssessmentNotFoundException;
import com.example.QuestionService.Exception.QuestionidNotFoundException;
import com.example.QuestionService.Exception.SetidNotFoundException;
import com.example.QuestionService.Model.Assessment;
import com.example.QuestionService.Model.Question;
import com.example.QuestionService.Model.Status;
import com.example.QuestionService.Repo.AssessmentRepo;
import com.example.QuestionService.Repo.QuestionRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;



@Service
public class AssessmentService {


    private final QuestionRepo questionRepo;

    private final AssessmentRepo assessmentRepo;

    private final ObjectMapper objectMapper;

    public AssessmentService(QuestionRepo questionRepo, AssessmentRepo assessmentRepo,ObjectMapper objectMapper) {
        this.questionRepo = questionRepo;
        this.assessmentRepo = assessmentRepo;
        this.objectMapper = objectMapper;

    }


    public Assessment createAssessment(AssessmentDto assessment) {
        try {

            Assessment assessment1 = EntityToDto.convertToEntity(assessment);

            assessment1.setStatus(Status.IN_PROGRESS);
            return assessmentRepo.save(assessment1);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to create assessment", e);
        }
    }

    public List<Assessment> getAllAssessments() {
        try{
            return assessmentRepo.findAll();
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to get all assessments", e);
        }

    }

    public Question updateAssessmentbyqid(Long setid, Long qid, QuestionDto question) {

        Optional<Assessment> setInfo= assessmentRepo.findById(setid);
        Optional<Question> updQuestion=questionRepo.findById(qid);
        Question question1 = EntityToDto.convertToEntity(question);
        if(updQuestion.isPresent()&& setInfo.isPresent()){
            updQuestion.get().setAnswers(question1.getAnswers());
            Question test = updQuestion.get();
            return questionRepo.save(test);
        }
        else{
           throw new QuestionidNotFoundException("Question to be updated not found");
        }
    }

    public String deleteAssessmentByQidAndSetId(Long setid, Long qid) {
        if (setid == null) {
            throw new SetidNotFoundException("Set name cannot be null or empty");
        }
        if (qid == null) {
            throw new QuestionidNotFoundException("Question ID cannot be null or empty");
        }
        Question question = (Question) questionRepo.findByQidAndSetid(qid, setid)
                .orElseThrow(() -> new QuestionidNotFoundException("Question not found with questionid: " + qid ));
        questionRepo.delete(question);
        return ("Question deleted successfully");
    }

    public AssessmentDto getAssessmentBySetname(String setname, Long qid) {
        Optional<Assessment> assessment = assessmentRepo.findBySetname(setname);
        if (assessment.isEmpty()) {
            throw new AssessmentNotFoundException("Assessment not found");
        }
        AssessmentDto assessmentDto = new AssessmentDto();
        assessmentDto.setSetname(assessment.get().getSetname());
        assessmentDto.setSetid(assessment.get().getSetid());
        assessmentDto.setStatus(assessment.get().getStatus());
        assessmentDto.setCreatedby(assessment.get().getCreatedby());
        assessmentDto.setUpdatedby(assessment.get().getUpdatedby());
        assessmentDto.setDomain(assessment.get().getDomain());
        assessmentDto.setStatus(assessment.get().getStatus());
        if(qid != null){
            Optional<Question> question = questionRepo.findByQidAndSetid(qid,assessment.get().getSetid());
            question.ifPresent(value -> assessmentDto.setQuestions(Collections.singletonList(question.get())));
        }
        else {
            List<Question> questions=questionRepo.findBySetid(assessment.get().getSetid());
            assessmentDto.setQuestions(questions);
        }
        return assessmentDto;
    }

    public Question updateAssessmentBySetname(String setname, Long qid, Question question) {
        Optional<Assessment> assessment = assessmentRepo.findBySetname(setname);
        if (assessment.isEmpty()) {
            throw new AssessmentNotFoundException("Assessment not found");
        }
        if(qid == null) {
            return questionRepo.save(question);
        }
        else {
            Optional<Question> question1 = questionRepo.findById(qid);

            if(question1.isPresent()) {
                question1.get().setQdetails(question.getQdetails());
                question1.get().setAnswers(question.getAnswers());
                return questionRepo.save(question1.get());
            }
            return questionRepo.save(question1.get());
        }
    }
}


