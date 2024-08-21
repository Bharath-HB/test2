package com.example.Survey_Service.Client;

import com.example.Survey_Service.Model.SurveyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullResponse {
    public Long surveyid;
    private String setname;
    public String requester;
    public String cname;
    public List<String> cemail;
    public String domain;
    public String createdby;
    public SurveyStatus status;
    private List<Question> questions;
    private List<Long> questionIds;


}
