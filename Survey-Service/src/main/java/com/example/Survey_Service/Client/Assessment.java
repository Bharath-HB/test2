package com.example.Survey_Service.Client;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "assessment")
public class Assessment {
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
