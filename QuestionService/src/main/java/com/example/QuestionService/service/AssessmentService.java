package com.example.QuestionService.service;

import com.example.QuestionService.dto.AssessmentDto;
import com.example.QuestionService.dto.EntityToDto;
import com.example.QuestionService.dto.QuestionDto;
import com.example.QuestionService.model.Assessment;
import com.example.QuestionService.model.Question;
import com.example.QuestionService.model.Status;
import com.example.QuestionService.repo.AssessmentRepo;
import com.example.QuestionService.repo.QuestionRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


import static com.example.QuestionService.dto.EntityToDto.convertToEntity;

@Service
public class AssessmentService {


    private final QuestionRepo questionRepo;


    private final AssessmentRepo assessmentRepo;

    // private final Assessment assessment;

    public AssessmentService(QuestionRepo questionRepo, AssessmentRepo assessmentRepo) {
        this.questionRepo = questionRepo;
        this.assessmentRepo = assessmentRepo;

    }


    public Assessment createAssessment(AssessmentDto assessment) {
        try {
            //  convertToEntity(assessment);
            Assessment assessment1 = EntityToDto.convertToEntity(assessment);
            //assessment1.setQuestions(null);
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

            updQuestion.get().setSetid(question1.getSetid());
            updQuestion.get().setQid(question1.getQid());
            updQuestion.get().setQdetails(question1.getQdetails());
            updQuestion.get().setAnswers(question1.getAnswers());
            Question test = updQuestion.get();
            return questionRepo.save(test);

        }
        else{
            throw new RuntimeException("Question not found with questionid: " + qid + " and setname: " + setid);
        }
    }

    public void deleteAssessmentByQidAndSetId(Long setid, Long qid) {

        if (setid == null) {
            throw new IllegalArgumentException("Set name cannot be null or empty");
        }
        if (qid == null) {
            throw new IllegalArgumentException("Question ID cannot be null or empty");
        }


        Question question = (Question) questionRepo.findByQidAndSetid(qid, setid)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with questionid: " + qid + " and setname: " + setid));
        questionRepo.delete(question);
    }

    public Assessment findAssessmentBySetId(Long setid) {
        return assessmentRepo.findById(setid).orElse(null);
    }

//    private Question saveQuestion(Question question) {
//        return questionRepo.save(question);
//    }
//
//    private Question findQuestionByQidAndSetId(String qid, String setid) {
//        return questionRepo.findByQidAndSetid(qid, setid)
//                .orElse(null);
//    }
}
