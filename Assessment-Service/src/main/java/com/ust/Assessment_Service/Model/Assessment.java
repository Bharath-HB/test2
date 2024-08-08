package com.ust.Assessment_Service.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "assessment")
public class Assessment {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String setid;
    private String setname;
    private String createdBy;
    private String domain;
    private String status;
    private String updatedBy;
    private String createdtimestamp;
    private String updatedtimestamp;
    //private List<Question> questions;
}