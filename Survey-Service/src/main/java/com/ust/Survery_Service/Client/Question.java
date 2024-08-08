package com.ust.Survery_Service.Client;

import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Question {
    private String qid;
    private String qdetails;
    private String setid;
}
