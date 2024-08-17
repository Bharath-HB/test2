package com.example.Survey_Service.Client;

import com.example.Survey_Service.Model.SurveyStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "assessment")
public class Assessment {
    private Long setid;
    private String setname;
    private String createdby;
    private String domain;
    private SurveyStatus status;
    private String updatedby;
    private Date createdtimestamp;
    private Date updatedtimestamp;
    private List<Question> questions;
    private List<Long> questionIds;
}
