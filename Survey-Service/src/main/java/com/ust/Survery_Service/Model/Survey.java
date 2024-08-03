package com.ust.Survery_Service.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "surveys")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long surveyid;
    public String cname;
    public String cemail;
    public String domain;
    public String setname;
    public String createdby;
    @Enumerated(value = EnumType.STRING)
    public SurveyStatus status;
}
