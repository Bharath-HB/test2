package com.example.Survey_Service.Model;

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
    public List<String> cemail;
    public String domain;
    private String setname;
    @Enumerated(value = EnumType.STRING)
    public SurveyStatus status;
    public String createdBy;
    private List<Long> questionIds;
}
