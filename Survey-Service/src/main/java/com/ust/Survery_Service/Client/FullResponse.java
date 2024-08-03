package com.ust.Survery_Service.Client;

import com.ust.Survery_Service.Model.SurveyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullResponse {
    public Long surveyid;
    public String cname;
    public String cemail;
    public String domain;
    public String setname;
    public String createdby;
    public SurveyStatus status;
}
