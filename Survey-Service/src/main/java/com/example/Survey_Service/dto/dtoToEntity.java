package com.example.Survey_Service.dto;

import com.example.Survey_Service.Client.FullResponse;
import com.example.Survey_Service.Model.Survey;
import org.springframework.stereotype.Component;



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
                .createdBy(response.getCreatedby())
                .status(response.getStatus())
                .setname(response.getSetname())
                .domain(response.getDomain())
                .questionIds(response.getQuestionIds())
                .build();
    }
}
