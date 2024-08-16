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
    public Long setid;
    public String requester;
    public String cname;
    public List<String> cemail;
    public String domain;
    public String createdby;
    public SurveyStatus status;
//    private String setName;
//    private List<Question> questions;
//    public List<Setinfo> setList;

    private List<Question> questions;


}
