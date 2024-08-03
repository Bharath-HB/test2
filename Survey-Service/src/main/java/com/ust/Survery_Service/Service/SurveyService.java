package com.ust.Survery_Service.Service;

import com.ust.Survery_Service.Model.Survey;
import com.ust.Survery_Service.Repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService {
    @Autowired
    private SurveyRepository repo;

    public Survey addSurvey(Survey survey) {
      return repo.save(survey);
    }

    public List<Survey> getSurveyDetails() {
        return repo.findAll();
    }

    public Survey getSurveyById(Long surveyid) {
        return repo.findById(surveyid).orElse(null);
    }
}
