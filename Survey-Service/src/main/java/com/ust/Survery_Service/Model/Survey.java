package com.ust.Survery_Service.Model;

import com.ust.Survery_Service.Client.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "surveys")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long surveyid;
    public String requester;
    public String cname;
    public String cemail;

    @Enumerated(value = EnumType.STRING)
    public SurveyStatus status;
    public String assesmentId;

    public String domain;
    public String setname;

    public String createdBy;
//    @Transient
//    private List<Question> questions;
}
