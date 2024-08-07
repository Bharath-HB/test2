package com.ust.Survery_Service.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "surveys")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long surveyid;
    public String requester;
    public String cname;
    public String cemail;
    public List<String> assessments;

    //   public String domain;
//    public String setname;
//    public String assesmentId;
//    public String createdby;
//    @Enumerated(value = EnumType.STRING)
//    public SurveyStatus status;
}
