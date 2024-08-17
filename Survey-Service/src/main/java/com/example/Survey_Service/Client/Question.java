package com.example.Survey_Service.Client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private String qid;
    private String qdetails;
    private Long setid;
}
