package com.ust.Survery_Service.dto;

import com.ust.Survery_Service.Client.FullResponse;
import com.ust.Survery_Service.Model.Survey;
import com.ust.Survery_Service.Model.SurveyStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class dtoToEntity {
    public static Survey convertToEntity(FullResponse response){
        if(response == null){
            return null;
        }
        return Survey.builder()
                .surveyid(response.getSurveyid())
                .requester(response.getRequester())
                .cname(response.getCname())
                .cemail(response.getCemail())
                //.questionList(response.getQuestionList())
                .setname(response.getSetname())
                .createdBy(response.getCreatedby())
                .status(response.getStatus())
                .assesmentId(response.getAssessmentId())
                .domain(response.getDomain())
                .build();

    }
}
