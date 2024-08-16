package com.example.Survey_Service.Client;

import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Question {
    private String qid;
    private String qdetails;
    private Long setid;
    //private List<Answers> answers;
}
