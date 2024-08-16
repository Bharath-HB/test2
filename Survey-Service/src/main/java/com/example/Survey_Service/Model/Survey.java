package com.example.Survey_Service.Model;

import com.example.Survey_Service.Client.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

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
    @Enumerated(value = EnumType.STRING)
    public SurveyStatus status;
    public Long setid;
//    public List<Setinfo> setList;
    public String createdBy;

//    @OneToMany
//    @JoinColumn(name="assessmentId")
//    @Cascade(value= org.hibernate.annotations.CascadeType.ALL)
//    private List<Question> questions;

}
